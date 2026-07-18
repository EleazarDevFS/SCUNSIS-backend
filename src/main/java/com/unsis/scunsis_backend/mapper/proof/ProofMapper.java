package com.unsis.scunsis_backend.mapper.proof;

import com.unsis.scunsis_backend.dto.request.proof.ProofRequest;
import com.unsis.scunsis_backend.dto.response.proof.ProofResponse;
import com.unsis.scunsis_backend.mapper.BaseMapper;
import com.unsis.scunsis_backend.model.activity.Activity;
import com.unsis.scunsis_backend.model.event.Event;
import com.unsis.scunsis_backend.model.proof.Proof;
import com.unsis.scunsis_backend.model.proof.ProofFile;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.model.sender.Sender;
import com.unsis.scunsis_backend.repository.proof.IProofFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProofMapper implements BaseMapper<ProofResponse, ProofRequest, Proof> {

    private final IProofFileRepository proofFileRepository;

    @Override
    public Proof toEntity(ProofRequest request) {
        return Proof.builder()
                .sender(Sender.builder().senderId(request.getSenderId()).build())
                .receiver(Receiver.builder().receiverId(request.getReceiverId()).build())
                .activity(Activity.builder().activityId(request.getActivityId()).build())
                .event(Event.builder().eventId(request.getEventId()).build())
                .role(request.getRole())
                .build();
    }

    @Override
    public ProofResponse toDto(Proof entity) {
        String fullName = entity.getReceiver().getName()
                + " " + entity.getReceiver().getLastName()
                + (entity.getReceiver().getTwoLastName() != null ? " " + entity.getReceiver().getTwoLastName() : "");

        String rutaPdf = proofFileRepository.findByFolio(entity.getFolio())
                .map(ProofFile::getRutaPdf)
                .orElse(null);

        return ProofResponse.builder()
                .folio(entity.getFolio())
                .senderId(entity.getSender() != null ? entity.getSender().getSenderId() : null)
                .senderName(entity.getSender() != null ? entity.getSender().getName() : null)
                .receiverId(entity.getReceiver() != null ? entity.getReceiver().getReceiverId() : null)
                .receiverFullName(fullName)
                .receiverEmail(entity.getReceiver() != null ? entity.getReceiver().getEmail() : null)
                .activityId(entity.getActivity() != null ? entity.getActivity().getActivityId() : null)
                .activityName(entity.getActivity() != null ? entity.getActivity().getActivityName() : null)
                .eventId(entity.getEvent() != null ? entity.getEvent().getEventId() : null)
                .eventName(entity.getEvent() != null ? entity.getEvent().getEventName() : null)
                .role(entity.getRole())
                .date(entity.getDate())
                .rutaPdf(rutaPdf)
                .build();
    }

    @Override
    public List<ProofResponse> toDtos(List<Proof> entities) {
        return entities.stream().map(this::toDto).toList();
    }

    @Override
    public void updateEntity(ProofRequest request, Proof entity) {
        if (request.getSenderId() != null) {
            entity.setSender(Sender.builder().senderId(request.getSenderId()).build());
        }
        if (request.getReceiverId() != null) {
            entity.setReceiver(Receiver.builder().receiverId(request.getReceiverId()).build());
        }
        if (request.getActivityId() != null) {
            entity.setActivity(Activity.builder().activityId(request.getActivityId()).build());
        }
        if (request.getEventId() != null) {
            entity.setEvent(Event.builder().eventId(request.getEventId()).build());
        }
        if (request.getRole() != null) {
            entity.setRole(request.getRole());
        }
    }
}
