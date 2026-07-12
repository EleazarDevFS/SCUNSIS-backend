package com.unsis.scunsis_backend.service.proof;

import com.unsis.scunsis_backend.dto.request.proof.ProofRequest;
import com.unsis.scunsis_backend.dto.response.proof.ProofBulkResponse;
import com.unsis.scunsis_backend.dto.response.proof.ProofResponse;
import com.unsis.scunsis_backend.exception.AppException;
import com.unsis.scunsis_backend.mapper.proof.ProofMapper;
import com.unsis.scunsis_backend.model.activity.Activity;
import com.unsis.scunsis_backend.model.enums.EParticipationRole;
import com.unsis.scunsis_backend.model.event.Event;
import com.unsis.scunsis_backend.model.proof.Proof;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.model.sender.Sender;
import com.unsis.scunsis_backend.repository.activity.IActivityRepository;
import com.unsis.scunsis_backend.repository.event.IEventRepository;
import com.unsis.scunsis_backend.repository.proof.IProofRepository;
import com.unsis.scunsis_backend.repository.receiver.IReceiverRepository;
import com.unsis.scunsis_backend.repository.sender.ISenderRepository;
import com.unsis.scunsis_backend.service.pdf.PdfGenerationService;
import com.unsis.scunsis_backend.util.FolioGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProofService {

    private final IProofRepository proofRepository;
    private final ProofMapper proofMapper;
    private final FolioGenerator folioGenerator;
    private final PdfGenerationService pdfGenerationService;
    private final ISenderRepository senderRepository;
    private final IReceiverRepository receiverRepository;
    private final IActivityRepository activityRepository;
    private final IEventRepository eventRepository;

    public List<ProofResponse> getAll() {
        return proofMapper.toDtos(proofRepository.findAll());
    }

    public ProofResponse getById(String folio) {
        Proof proof = proofRepository.findById(folio)
                .orElseThrow(() -> new AppException("Constancia no encontrada con folio: " + folio, HttpStatus.NOT_FOUND));
        return proofMapper.toDto(proof);
    }

    @Transactional
    public void deleteById(String folio) {
        if (!proofRepository.existsByFolio(folio)) {
            throw new AppException("Constancia no encontrada con folio: " + folio, HttpStatus.NOT_FOUND);
        }
        proofRepository.deleteById(folio);
    }

    @Transactional
    public ProofResponse createProof(ProofRequest request) {
        Sender sender = senderRepository.findById(request.getSenderId())
                .orElseThrow(() -> new AppException("Emisor no encontrado", HttpStatus.NOT_FOUND));
        Receiver receiver = receiverRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new AppException("Receptor no encontrado", HttpStatus.NOT_FOUND));
        Activity activity = activityRepository.findById(request.getActivityId())
                .orElseThrow(() -> new AppException("Actividad no encontrada", HttpStatus.NOT_FOUND));
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new AppException("Evento no encontrado", HttpStatus.NOT_FOUND));

        EParticipationRole role = request.getRole();
        int currentYear = LocalDate.now().getYear();
        long count = proofRepository.countByRoleAndYear(role, currentYear);
        String folio = folioGenerator.generateFolio(role, count + 1, currentYear);

        Proof proof = Proof.builder()
                .folio(folio)
                .sender(sender)
                .receiver(receiver)
                .activity(activity)
                .event(event)
                .role(role)
                .date(LocalDate.now())
                .build();

        proofRepository.save(proof);
        return proofMapper.toDto(proof);
    }

    @Transactional
    public ProofBulkResponse createProofsBulk(
            Long eventId,
            Long activityId,
            Long senderId,
            List<Receiver> receivers,
            EParticipationRole role
    ) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new AppException("Evento no encontrado", HttpStatus.NOT_FOUND));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new AppException("Actividad no encontrada", HttpStatus.NOT_FOUND));
        Sender sender = senderRepository.findById(senderId)
                .orElseThrow(() -> new AppException("Emisor no encontrado", HttpStatus.NOT_FOUND));

        int currentYear = LocalDate.now().getYear();
        long currentCount = proofRepository.countByRoleAndYear(role, currentYear);

        List<String> generatedFolios = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        int successCount = 0;

        for (int i = 0; i < receivers.size(); i++) {
            try {
                String folio = folioGenerator.generateFolio(role, currentCount + i + 1, currentYear);

                Proof proof = Proof.builder()
                        .folio(folio)
                        .sender(sender)
                        .receiver(receivers.get(i))
                        .activity(activity)
                        .event(event)
                        .role(role)
                        .date(LocalDate.now())
                        .build();

                proofRepository.save(proof);
                generatedFolios.add(folio);
                successCount++;
            } catch (Exception e) {
                errors.add("Fila " + (i + 1) + ": " + e.getMessage());
            }
        }

        return ProofBulkResponse.builder()
                .totalRows(receivers.size())
                .successCount(successCount)
                .errorCount(errors.size())
                .generatedFolios(generatedFolios)
                .errors(errors)
                .build();
    }

    public List<ProofResponse> getByActivity(Long activityId) {
        return proofMapper.toDtos(proofRepository.findByActivityActivityId(activityId));
    }

    public byte[] generatePdf(String folio) {
        Proof proof = proofRepository.findById(folio)
                .orElseThrow(() -> new AppException("Constancia no encontrada con folio: " + folio, HttpStatus.NOT_FOUND));
        return pdfGenerationService.generateCertificate(proof);
    }
}
