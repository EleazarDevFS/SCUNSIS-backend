package com.unsis.scunsis_backend.config;

import com.unsis.scunsis_backend.model.activity.Activity;
import com.unsis.scunsis_backend.model.auth.User;
import com.unsis.scunsis_backend.model.enums.EEventType;
import com.unsis.scunsis_backend.model.enums.EParticipationRole;
import com.unsis.scunsis_backend.model.enums.ERole;
import com.unsis.scunsis_backend.model.event.Event;
import com.unsis.scunsis_backend.model.proof.Proof;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.model.sender.Sender;
import com.unsis.scunsis_backend.repository.activity.IActivityRepository;
import com.unsis.scunsis_backend.repository.auth.IUserRepository;
import com.unsis.scunsis_backend.repository.event.IEventRepository;
import com.unsis.scunsis_backend.repository.proof.IProofRepository;
import com.unsis.scunsis_backend.repository.receiver.IReceiverRepository;
import com.unsis.scunsis_backend.repository.sender.ISenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ISenderRepository senderRepository;
    private final IReceiverRepository receiverRepository;
    private final IEventRepository eventRepository;
    private final IActivityRepository activityRepository;
    private final IProofRepository proofRepository;

    @Override
    public void run(String... args) {
        seedUsers();
        seedSenders();
        seedReceivers();
        seedEvents();
        seedActivities();
        seedProofs();
    }

    private void seedUsers() {
        if (userRepository.existsByUsername("admin")) return;
        userRepository.save(User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .role(ERole.ADMIN)
                .build());
        System.out.println("Usuario admin creado (admin/admin)");

        if (userRepository.existsByUsername("capturista")) return;
        userRepository.save(User.builder()
                .username("capturista")
                .password(passwordEncoder.encode("capturista"))
                .role(ERole.CAPTURISTA)
                .build());
        System.out.println("Usuario capturista creado (capturista/capturista)");
    }

    private void seedSenders() {
        if (senderRepository.existsById(1L)) return;
        senderRepository.save(Sender.builder().senderId(1L).name("Licenciatura en Informatica").campus("Campus Central").build());
        senderRepository.save(Sender.builder().senderId(2L).name("Coordinacion de Posgrado").campus("Campus Central").build());
        senderRepository.save(Sender.builder().senderId(3L).name("Instituto de Investigacion").campus("Campus Sur").build());
        senderRepository.save(Sender.builder().senderId(4L).name("Departamento de Extension Universitaria").campus("Campus Central").build());
        System.out.println("Emisores creados");
    }

    private void seedReceivers() {
        if (receiverRepository.existsById(1L)) return;
        receiverRepository.save(Receiver.builder().receiverId(1L).name("Juan").lastName("Perez").twoLastName("Garcia").phone("5551234567").email("juan.perez@example.com").academicGrade("Licenciatura").build());
        receiverRepository.save(Receiver.builder().receiverId(2L).name("Maria").lastName("Lopez").twoLastName("Martinez").phone("5559876543").email("maria.lopez@example.com").academicGrade("Maestria").build());
        receiverRepository.save(Receiver.builder().receiverId(3L).name("Carlos").lastName("Hernandez").twoLastName("Rodriguez").phone("5555551234").email("carlos.hernandez@example.com").academicGrade("Doctorado").build());
        receiverRepository.save(Receiver.builder().receiverId(4L).name("Ana").lastName("Gonzalez").twoLastName("Sanchez").phone("5554443322").email("ana.gonzalez@example.com").academicGrade("Licenciatura").build());
        receiverRepository.save(Receiver.builder().receiverId(5L).name("Luis").lastName("Ramirez").twoLastName("Torres").phone("5556667788").email("luis.ramirez@example.com").academicGrade("Ingenieria").build());
        receiverRepository.save(Receiver.builder().receiverId(6L).name("Sofia").lastName("Flores").twoLastName("Cruz").phone("5557778899").email("sofia.flores@example.com").academicGrade("Maestria").build());
        receiverRepository.save(Receiver.builder().receiverId(7L).name("Pedro").lastName("Morales").twoLastName("Vega").phone("5558889900").email("pedro.morales@example.com").academicGrade("Licenciatura").build());
        receiverRepository.save(Receiver.builder().receiverId(8L).name("Laura").lastName("Jimenez").twoLastName("Mendoza").phone("5559990011").email("laura.jimenez@example.com").academicGrade("Especialidad").build());
        System.out.println("Receptores creados");
    }

    private void seedEvents() {
        if (eventRepository.existsById(1L)) return;
        eventRepository.save(Event.builder().eventId(1L).eventType(EEventType.FISICO).eventName("Congreso Internacional de Ingenieria de Software").startDate(LocalDate.of(2025, 3, 15)).endDate(LocalDate.of(2025, 3, 17)).eventPlace("Auditorio Principal, Campus Central").eventDescription("Compartir avances en el desarrollo de software y metodologias agiles").build());
        eventRepository.save(Event.builder().eventId(2L).eventType(EEventType.VIRTUAL).eventName("Jornadas de Inteligencia Artificial").startDate(LocalDate.of(2025, 5, 20)).endDate(LocalDate.of(2025, 5, 22)).eventPlace("Plataforma Zoom").eventDescription("Difundir conocimientos sobre IA y Machine Learning").build());
        eventRepository.save(Event.builder().eventId(3L).eventType(EEventType.FISICO).eventName("Simposio de Ciberseguridad").startDate(LocalDate.of(2025, 6, 10)).endDate(LocalDate.of(2025, 6, 11)).eventPlace("Centro de Convenciones").eventDescription("Abordar temas de seguridad informatica y proteccion de datos").build());
        eventRepository.save(Event.builder().eventId(4L).eventType(EEventType.VIRTUAL).eventName("Conferencia de Desarrollo Web Moderno").startDate(LocalDate.of(2025, 8, 5)).endDate(LocalDate.of(2025, 8, 6)).eventPlace("Google Meet").eventDescription("Explorar frameworks y tecnologias web actuales").build());
        eventRepository.save(Event.builder().eventId(5L).eventType(EEventType.FISICO).eventName("Foro de Emprendimiento Tecnologico").startDate(LocalDate.of(2025, 9, 18)).endDate(LocalDate.of(2025, 9, 19)).eventPlace("Sala de Usos Multiples").eventDescription("Promover el emprendimiento e innovacion tecnologica").build());
        System.out.println("Eventos creados");
    }

    private void seedActivities() {
        if (activityRepository.existsById(1L)) return;
        activityRepository.save(Activity.builder().activityId(1L).event(eventRepository.getReferenceById(1L)).activityName("Ponencia: Metodologias Agiles").activityDescription("Presentar las mejores practicas en Scrum y Kanban").startDate(LocalDate.of(2025, 3, 15)).endDate(LocalDate.of(2025, 3, 15)).activityPlace("Sala A").build());
        activityRepository.save(Activity.builder().activityId(2L).event(eventRepository.getReferenceById(1L)).activityName("Taller: Git Avanzado").activityDescription("Ensenar tecnicas avanzadas de control de versiones").startDate(LocalDate.of(2025, 3, 16)).endDate(LocalDate.of(2025, 3, 16)).activityPlace("Laboratorio 1").build());
        activityRepository.save(Activity.builder().activityId(3L).event(eventRepository.getReferenceById(1L)).activityName("Panel: Futuro del Software").activityDescription("Discutir tendencias y desafios de la ingenieria de software").startDate(LocalDate.of(2025, 3, 17)).endDate(LocalDate.of(2025, 3, 17)).activityPlace("Auditorio").build());
        activityRepository.save(Activity.builder().activityId(4L).event(eventRepository.getReferenceById(2L)).activityName("Conferencia: Redes Neuronales").activityDescription("Introduccion a deep learning").startDate(LocalDate.of(2025, 5, 20)).endDate(LocalDate.of(2025, 5, 20)).activityPlace("Plataforma Virtual").build());
        activityRepository.save(Activity.builder().activityId(5L).event(eventRepository.getReferenceById(2L)).activityName("Taller: Python para IA").activityDescription("Practica con librerias de machine learning").startDate(LocalDate.of(2025, 5, 21)).endDate(LocalDate.of(2025, 5, 21)).activityPlace("Plataforma Virtual").build());
        activityRepository.save(Activity.builder().activityId(6L).event(eventRepository.getReferenceById(3L)).activityName("Ponencia: Ethical Hacking").activityDescription("Tecnicas de pentesting etico").startDate(LocalDate.of(2025, 6, 10)).endDate(LocalDate.of(2025, 6, 10)).activityPlace("Sala B").build());
        activityRepository.save(Activity.builder().activityId(7L).event(eventRepository.getReferenceById(3L)).activityName("Workshop: Seguridad en la Nube").activityDescription("Proteccion de infraestructuras cloud").startDate(LocalDate.of(2025, 6, 11)).endDate(LocalDate.of(2025, 6, 11)).activityPlace("Sala C").build());
        activityRepository.save(Activity.builder().activityId(8L).event(eventRepository.getReferenceById(4L)).activityName("Conferencia: React y Next.js").activityDescription("Desarrollo de aplicaciones modernas").startDate(LocalDate.of(2025, 8, 5)).endDate(LocalDate.of(2025, 8, 5)).activityPlace("Google Meet").build());
        activityRepository.save(Activity.builder().activityId(9L).event(eventRepository.getReferenceById(5L)).activityName("Panel: Startups Tech").activityDescription("Experiencias de emprendedores exitosos").startDate(LocalDate.of(2025, 9, 18)).endDate(LocalDate.of(2025, 9, 18)).activityPlace("Sala Principal").build());
        System.out.println("Actividades creadas");
    }

    private void seedProofs() {
        if (proofRepository.existsById("PON-2025-0001")) return;
        proofRepository.save(Proof.builder().folio("PON-2025-0001").sender(senderRepository.getReferenceById(1L)).receiver(receiverRepository.getReferenceById(1L)).activity(activityRepository.getReferenceById(1L)).event(eventRepository.getReferenceById(1L)).role(EParticipationRole.PONENTE).date(LocalDate.of(2025, 3, 17)).build());
        proofRepository.save(Proof.builder().folio("PON-2025-0002").sender(senderRepository.getReferenceById(1L)).receiver(receiverRepository.getReferenceById(3L)).activity(activityRepository.getReferenceById(3L)).event(eventRepository.getReferenceById(1L)).role(EParticipationRole.PONENTE).date(LocalDate.of(2025, 3, 17)).build());
        proofRepository.save(Proof.builder().folio("PAR-2025-0001").sender(senderRepository.getReferenceById(1L)).receiver(receiverRepository.getReferenceById(2L)).activity(activityRepository.getReferenceById(2L)).event(eventRepository.getReferenceById(1L)).role(EParticipationRole.PARTICIPANTE).date(LocalDate.of(2025, 3, 17)).build());
        proofRepository.save(Proof.builder().folio("PAR-2025-0002").sender(senderRepository.getReferenceById(1L)).receiver(receiverRepository.getReferenceById(4L)).activity(activityRepository.getReferenceById(1L)).event(eventRepository.getReferenceById(1L)).role(EParticipationRole.PARTICIPANTE).date(LocalDate.of(2025, 3, 17)).build());
        proofRepository.save(Proof.builder().folio("PAR-2025-0003").sender(senderRepository.getReferenceById(1L)).receiver(receiverRepository.getReferenceById(5L)).activity(activityRepository.getReferenceById(2L)).event(eventRepository.getReferenceById(1L)).role(EParticipationRole.PARTICIPANTE).date(LocalDate.of(2025, 3, 17)).build());
        proofRepository.save(Proof.builder().folio("PON-2025-0003").sender(senderRepository.getReferenceById(4L)).receiver(receiverRepository.getReferenceById(6L)).activity(activityRepository.getReferenceById(6L)).event(eventRepository.getReferenceById(3L)).role(EParticipationRole.PONENTE).date(LocalDate.of(2025, 6, 11)).build());
        proofRepository.save(Proof.builder().folio("PAR-2025-0004").sender(senderRepository.getReferenceById(4L)).receiver(receiverRepository.getReferenceById(7L)).activity(activityRepository.getReferenceById(7L)).event(eventRepository.getReferenceById(3L)).role(EParticipationRole.PARTICIPANTE).date(LocalDate.of(2025, 6, 11)).build());
        proofRepository.save(Proof.builder().folio("ORG-2025-0001").sender(senderRepository.getReferenceById(3L)).receiver(receiverRepository.getReferenceById(8L)).activity(activityRepository.getReferenceById(8L)).event(eventRepository.getReferenceById(4L)).role(EParticipationRole.ORGANIZADOR).date(LocalDate.of(2025, 8, 6)).build());
        proofRepository.save(Proof.builder().folio("REC-2025-0001").sender(senderRepository.getReferenceById(2L)).receiver(receiverRepository.getReferenceById(1L)).activity(activityRepository.getReferenceById(9L)).event(eventRepository.getReferenceById(5L)).role(EParticipationRole.RECONOCIMIENTO).date(LocalDate.of(2025, 9, 19)).build());
        proofRepository.save(Proof.builder().folio("REC-2025-0002").sender(senderRepository.getReferenceById(2L)).receiver(receiverRepository.getReferenceById(4L)).activity(activityRepository.getReferenceById(9L)).event(eventRepository.getReferenceById(5L)).role(EParticipationRole.RECONOCIMIENTO).date(LocalDate.of(2025, 9, 19)).build());
        System.out.println("Constancias creadas");
    }
}
