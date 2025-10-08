package com.unsis.scunsis_backend.service.sender;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unsis.scunsis_backend.dto.request.sender.SenderRequest;
import com.unsis.scunsis_backend.dto.response.sender.SenderResponse;
import com.unsis.scunsis_backend.mapper.sender.SenderMapper;
import com.unsis.scunsis_backend.model.sender.Sender;
import com.unsis.scunsis_backend.repository.sender.ISenderRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@Service
@RequiredArgsConstructor
public class SenderService {

    private final ISenderRepository senderRepository;
    private final SenderMapper senderMapper;

    public SenderResponse getById(long senderId){
        Optional<Sender> sender = senderRepository.findById(senderId);
        SenderResponse senderResponse = senderMapper.toDto(sender.get());
        return senderResponse;
    }

    public List<SenderResponse> getAll(){
        List<Sender> senders = senderRepository.findAll();
        List<SenderResponse> sendersResponse = senderMapper.toDtos(senders);
        return sendersResponse;
    }

    public void deleteById(long senderId){
        // Validamos si existe(lo buscamos)
        senderRepository.deleteById(senderId);
    }

    public void createSender(SenderRequest request){
        // Validar si existe
        senderRepository.save(senderMapper.toEntity(request));
    }
}
