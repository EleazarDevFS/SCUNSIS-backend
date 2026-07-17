package com.unsis.scunsis_backend.service.receiver;

import com.unsis.scunsis_backend.dto.request.receiver.ReceiverRequest;
import com.unsis.scunsis_backend.dto.response.receiver.ReceiverResponse;
import com.unsis.scunsis_backend.exception.AppException;
import com.unsis.scunsis_backend.mapper.receiver.ReceiverMapper;
import com.unsis.scunsis_backend.model.enums.EParticipationRole;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.repository.proof.IProofRepository;
import com.unsis.scunsis_backend.repository.receiver.IReceiverRepository;
import com.unsis.scunsis_backend.util.FolioGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiverService {

    private final IReceiverRepository receiverRepository;
    private final ReceiverMapper receiverMapper;
    private final FolioGenerator folioGenerator;
    private final IProofRepository proofRepository;

    public ReceiverResponse getById(long receiverId) {
        return receiverMapper.toDto(receiverRepository.findById(receiverId)
                .orElseThrow(() -> new AppException("Receptor no encontrado con id: " + receiverId, HttpStatus.NOT_FOUND)));
    }

    public List<ReceiverResponse> getAll() {
        return receiverMapper.toDtos(receiverRepository.findAll());
    }

    @Transactional
    public void deleteById(long receiverId) {
        if (!receiverRepository.existsById(receiverId)) {
            throw new AppException("Receptor no encontrado con id: " + receiverId, HttpStatus.NOT_FOUND);
        }
        receiverRepository.deleteById(receiverId);
    }

    @Transactional
    public ReceiverResponse createReceiver(ReceiverRequest request) {
        String name = request.getName();
        String lastName = request.getLastName();
        if (name == null || name.isBlank()) {
            throw new AppException("El nombre del receptor es requerido", HttpStatus.BAD_REQUEST);
        }
        if (lastName == null || lastName.isBlank()) {
            throw new AppException("El primer apellido del receptor es requerido", HttpStatus.BAD_REQUEST);
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()
                && receiverRepository.existsByEmail(request.getEmail().trim())) {
            throw new AppException("Este correo ya esta registrado", HttpStatus.CONFLICT);
        }
        Receiver receiver = receiverMapper.toEntity(request);
        receiver = receiverRepository.save(receiver);
        ReceiverResponse response = receiverMapper.toDto(receiver);
        response.setFolio(generateFolio());
        return response;
    }

    @Transactional
    public ReceiverResponse updateReceiver(long receiverId, ReceiverRequest request) {
        Receiver receiver = receiverRepository.findById(receiverId)
                .orElseThrow(() -> new AppException("Receptor no encontrado con id: " + receiverId, HttpStatus.NOT_FOUND));
        receiverMapper.updateEntity(request, receiver);
        receiver = receiverRepository.save(receiver);
        ReceiverResponse response = receiverMapper.toDto(receiver);
        response.setFolio(generateFolio());
        return response;
    }

    private String generateFolio() {
        int year = Year.now().getValue();
        long count = proofRepository.countByRoleAndYear(EParticipationRole.PARTICIPANTE, year);
        return folioGenerator.generateFolio(EParticipationRole.PARTICIPANTE, count + 1, year);
    }
}
