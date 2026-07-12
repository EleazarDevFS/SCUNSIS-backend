package com.unsis.scunsis_backend.service.sender;

import com.unsis.scunsis_backend.dto.request.sender.SenderRequest;
import com.unsis.scunsis_backend.dto.response.sender.SenderResponse;
import com.unsis.scunsis_backend.exception.AppException;
import com.unsis.scunsis_backend.mapper.sender.SenderMapper;
import com.unsis.scunsis_backend.model.sender.Sender;
import com.unsis.scunsis_backend.repository.sender.ISenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SenderService {

    private final ISenderRepository senderRepository;
    private final SenderMapper senderMapper;

    public SenderResponse getById(long senderId) {
        return senderMapper.toDto(senderRepository.findById(senderId)
                .orElseThrow(() -> new AppException("Emisor no encontrado con id: " + senderId, HttpStatus.NOT_FOUND)));
    }

    public List<SenderResponse> getAll() {
        return senderMapper.toDtos(senderRepository.findAll());
    }

    @Transactional
    public void deleteById(long senderId) {
        if (!senderRepository.existsById(senderId)) {
            throw new AppException("Emisor no encontrado con id: " + senderId, HttpStatus.NOT_FOUND);
        }
        senderRepository.deleteById(senderId);
    }

    @Transactional
    public void createSender(SenderRequest request) {
        String name = request.getName();
        if (name == null || name.isBlank()) {
            throw new AppException("El nombre del emisor es requerido", HttpStatus.BAD_REQUEST);
        }
        List<Sender> senderList = senderRepository.getSenderByName(name.trim());
        String campus = request.getCampus();
        if (senderList.stream().anyMatch(s -> s.getCampus() != null
                && s.getCampus().trim().equalsIgnoreCase(campus != null ? campus.trim() : ""))) {
            throw new AppException("Este emisor ya esta registrado", HttpStatus.CONFLICT);
        }
        senderRepository.save(senderMapper.toEntity(request));
    }
}
