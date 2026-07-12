package com.unsis.scunsis_backend.service.receiver;

import com.unsis.scunsis_backend.dto.request.receiver.ReceiverRequest;
import com.unsis.scunsis_backend.dto.response.receiver.ReceiverResponse;
import com.unsis.scunsis_backend.exception.AppException;
import com.unsis.scunsis_backend.mapper.receiver.ReceiverMapper;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.repository.receiver.IReceiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiverService {

    private final IReceiverRepository receiverRepository;
    private final ReceiverMapper receiverMapper;

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
        if (request.getEmail() != null && !request.getEmail().isBlank()
                && receiverRepository.existsByEmail(request.getEmail().trim())) {
            throw new AppException("Este correo ya esta registrado", HttpStatus.CONFLICT);
        }
        Receiver receiver = receiverMapper.toEntity(request);
        receiver = receiverRepository.save(receiver);
        return receiverMapper.toDto(receiver);
    }
}
