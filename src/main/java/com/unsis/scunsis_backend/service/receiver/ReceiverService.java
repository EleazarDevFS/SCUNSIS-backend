package com.unsis.scunsis_backend.service.receiver;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unsis.scunsis_backend.dto.request.receiver.ReceiverRequest;
import com.unsis.scunsis_backend.dto.response.receiver.ReceiverResponse;
import com.unsis.scunsis_backend.mapper.receiver.ReceiverMapper;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.repository.receiver.IReceiverRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@Service
@RequiredArgsConstructor
public class ReceiverService {

    private final IReceiverRepository receiverRepository;
    private final ReceiverMapper receiverMapper;

    public ReceiverResponse getById(long receiverId){
        Optional<Receiver> receiver = receiverRepository.findById(receiverId);
        ReceiverResponse receiverResponse = receiverMapper.toDto(receiver.get());
        return receiverResponse;
    }

    public List<ReceiverResponse> getAll(){
        List<Receiver> receivers = receiverRepository.findAll();
        List<ReceiverResponse> receiversResponse = receiverMapper.toDtos(receivers);
        return receiversResponse;
    }

    public void deleteById(long receiverId){
        // Validamos si existe
        receiverRepository.deleteById(receiverId);
    }

    public void createReceiver(ReceiverRequest request){
        // Validamos los datos con jakarta o un services de validación
        receiverRepository.save(receiverMapper.toEntity(request));
    }

}
