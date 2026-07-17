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

    private Sender sender1, sender2, sender3, sender4;
    private Receiver receiver1, receiver2, receiver3, receiver4, receiver5, receiver6, receiver7, receiver8;
    private Event event1, event2, event3, event4, event5;
    private Activity activity1, activity2, activity3, activity4, activity5, activity6, activity7, activity8, activity9;

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
        if (senderRepository.count() > 0) return;
        sender1 = senderRepository.save(Sender.builder().name("Licenciatura en Informatica").campus("Campus Central").build());
        sender2 = senderRepository.save(Sender.builder().name("Coordinacion de Posgrado").campus("Campus Central").build());
        sender3 = senderRepository.save(Sender.builder().name("Instituto de Investigacion").campus("Campus Sur").build());
        sender4 = senderRepository.save(Sender.builder().name("Departamento de Extension Universitaria").campus("Campus Central").build());
        System.out.println("Emisores creados");
    }

    private void seedReceivers() {
        if (receiverRepository.count() > 0) return;
        receiver1 = receiverRepository.save(Receiver.builder().name("Juan").lastName("Perez").twoLastName("Garcia").phone("5551234567").email("juan.perez@example.com").academicGrade("Licenciatura").build());
        receiver2 = receiverRepository.save(Receiver.builder().name("Maria").lastName("Lopez").twoLastName("Martinez").phone("5559876543").email("maria.lopez@example.com").academicGrade("Maestria").build());
        receiver3 = receiverRepository.save(Receiver.builder().name("Carlos").lastName("Hernandez").twoLastName("Rodriguez").phone("5555551234").email("carlos.hernandez@example.com").academicGrade("Doctorado").build());
        receiver4 = receiverRepository.save(Receiver.builder().name("Ana").lastName("Gonzalez").twoLastName("Sanchez").phone("5554443322").email("ana.gonzalez@example.com").academicGrade("Licenciatura").build());
        receiver5 = receiverRepository.save(Receiver.builder().name("Luis").lastName("Ramirez").twoLastName("Torres").phone("5556667788").email("luis.ramirez@example.com").academicGrade("Ingenieria").build());
        receiver6 = receiverRepository.save(Receiver.builder().name("Sofia").lastName("Flores").twoLastName("Cruz").phone("5557778899").email("sofia.flores@example.com").academicGrade("Maestria").build());
        receiver7 = receiverRepository.save(Receiver.builder().name("Pedro").lastName("Morales").twoLastName("Vega").phone("5558889900").email("pedro.morales@example.com").academicGrade("Licenciatura").build());
        receiver8 = receiverRepository.save(Receiver.builder().name("Laura").lastName("Jimenez").twoLastName("Mendoza").phone("5559990011").email("laura.jimenez@example.com").academicGrade("Especialidad").build());
        System.out.println("Receptores creados");
    }

    private void seedEvents() {
        if (eventRepository.count() > 0) return;
        event1 = eventRepository.save(Event.builder().eventType(EEventType.FISICO).eventName("Congreso Internacional de Ingenieria de Software").startDate(LocalDate.of(2025, 3, 15)).endDate(LocalDate.of(2025, 3, 17)).eventPlace("Auditorio Principal, Campus Central").eventDescription("Compartir avances en el desarrollo de software y metodologias agiles").build());
        event2 = eventRepository.save(Event.builder().eventType(EEventType.VIRTUAL).eventName("Jornadas de Inteligencia Artificial").startDate(LocalDate.of(2025, 5, 20)).endDate(LocalDate.of(2025, 5, 22)).eventPlace("Plataforma Zoom").eventDescription("Difundir conocimientos sobre IA y Machine Learning").build());
        event3 = eventRepository.save(Event.builder().eventType(EEventType.FISICO).eventName("Simposio de Ciberseguridad").startDate(LocalDate.of(2025, 6, 10)).endDate(LocalDate.of(2025, 6, 11)).eventPlace("Centro de Convenciones").eventDescription("Abordar temas de seguridad informatica y proteccion de datos").build());
        event4 = eventRepository.save(Event.builder().eventType(EEventType.VIRTUAL).eventName("Conferencia de Desarrollo Web Moderno").startDate(LocalDate.of(2025, 8, 5)).endDate(LocalDate.of(2025, 8, 6)).eventPlace("Google Meet").eventDescription("Explorar frameworks y tecnologias web actuales").build());
        event5 = eventRepository.save(Event.builder().eventType(EEventType.FISICO).eventName("Foro de Emprendimiento Tecnologico").startDate(LocalDate.of(2025, 9, 18)).endDate(LocalDate.of(2025, 9, 19)).eventPlace("Sala de Usos Multiples").eventDescription("Promover el emprendimiento e innovacion tecnologica").build());
        System.out.println("Eventos creados");
    }

    private void seedActivities() {
        if (activityRepository.count() > 0) return;
        activity1 = activityRepository.save(Activity.builder().event(event1).activityName("Ponencia: Metodologias Agiles").activityDescription("Presentar las mejores practicas en Scrum y Kanban").startDate(LocalDate.of(2025, 3, 15)).endDate(LocalDate.of(2025, 3, 15)).activityPlace("Sala A").build());
        activity2 = activityRepository.save(Activity.builder().event(event1).activityName("Taller: Git Avanzado").activityDescription("Ensenar tecnicas avanzadas de control de versiones").startDate(LocalDate.of(2025, 3, 16)).endDate(LocalDate.of(2025, 3, 16)).activityPlace("Laboratorio 1").build());
        activity3 = activityRepository.save(Activity.builder().event(event1).activityName("Panel: Futuro del Software").activityDescription("Discutir tendencias y desafios de la ingenieria de software").startDate(LocalDate.of(2025, 3, 17)).endDate(LocalDate.of(2025, 3, 17)).activityPlace("Auditorio").build());
        activity4 = activityRepository.save(Activity.builder().event(event2).activityName("Conferencia: Redes Neuronales").activityDescription("Introduccion a deep learning").startDate(LocalDate.of(2025, 5, 20)).endDate(LocalDate.of(2025, 5, 20)).activityPlace("Plataforma Virtual").build());
        activity5 = activityRepository.save(Activity.builder().event(event2).activityName("Taller: Python para IA").activityDescription("Practica con librerias de machine learning").startDate(LocalDate.of(2025, 5, 21)).endDate(LocalDate.of(2025, 5, 21)).activityPlace("Plataforma Virtual").build());
        activity6 = activityRepository.save(Activity.builder().event(event3).activityName("Ponencia: Ethical Hacking").activityDescription("Tecnicas de pentesting etico").startDate(LocalDate.of(2025, 6, 10)).endDate(LocalDate.of(2025, 6, 10)).activityPlace("Sala B").build());
        activity7 = activityRepository.save(Activity.builder().event(event3).activityName("Workshop: Seguridad en la Nube").activityDescription("Proteccion de infraestructuras cloud").startDate(LocalDate.of(2025, 6, 11)).endDate(LocalDate.of(2025, 6, 11)).activityPlace("Sala C").build());
        activity8 = activityRepository.save(Activity.builder().event(event4).activityName("Conferencia: React y Next.js").activityDescription("Desarrollo de aplicaciones modernas").startDate(LocalDate.of(2025, 8, 5)).endDate(LocalDate.of(2025, 8, 5)).activityPlace("Google Meet").build());
        activity9 = activityRepository.save(Activity.builder().event(event5).activityName("Panel: Startups Tech").activityDescription("Experiencias de emprendedores exitosos").startDate(LocalDate.of(2025, 9, 18)).endDate(LocalDate.of(2025, 9, 18)).activityPlace("Sala Principal").build());
        System.out.println("Actividades creadas");
    }

    private void seedProofs() {
        if (proofRepository.count() > 0) return;
        proofRepository.save(Proof.builder().folio("PON-2025-0001").sender(sender1).receiver(receiver1).activity(activity1).event(event1).role(EParticipationRole.PONENTE).date(LocalDate.of(2025, 3, 17)).build());
        proofRepository.save(Proof.builder().folio("PON-2025-0002").sender(sender1).receiver(receiver3).activity(activity3).event(event1).role(EParticipationRole.PONENTE).date(LocalDate.of(2025, 3, 17)).build());
        proofRepository.save(Proof.builder().folio("PAR-2025-0001").sender(sender1).receiver(receiver2).activity(activity2).event(event1).role(EParticipationRole.PARTICIPANTE).date(LocalDate.of(2025, 3, 17)).build());
        proofRepository.save(Proof.builder().folio("PAR-2025-0002").sender(sender1).receiver(receiver4).activity(activity1).event(event1).role(EParticipationRole.PARTICIPANTE).date(LocalDate.of(2025, 3, 17)).build());
        proofRepository.save(Proof.builder().folio("PAR-2025-0003").sender(sender1).receiver(receiver5).activity(activity2).event(event1).role(EParticipationRole.PARTICIPANTE).date(LocalDate.of(2025, 3, 17)).build());
        proofRepository.save(Proof.builder().folio("PON-2025-0003").sender(sender4).receiver(receiver6).activity(activity6).event(event3).role(EParticipationRole.PONENTE).date(LocalDate.of(2025, 6, 11)).build());
        proofRepository.save(Proof.builder().folio("PAR-2025-0004").sender(sender4).receiver(receiver7).activity(activity7).event(event3).role(EParticipationRole.PARTICIPANTE).date(LocalDate.of(2025, 6, 11)).build());
        proofRepository.save(Proof.builder().folio("ORG-2025-0001").sender(sender3).receiver(receiver8).activity(activity8).event(event4).role(EParticipationRole.ORGANIZADOR).date(LocalDate.of(2025, 8, 6)).build());
        proofRepository.save(Proof.builder().folio("REC-2025-0001").sender(sender2).receiver(receiver1).activity(activity9).event(event5).role(EParticipationRole.RECONOCIMIENTO).date(LocalDate.of(2025, 9, 19)).build());
        proofRepository.save(Proof.builder().folio("REC-2025-0002").sender(sender2).receiver(receiver4).activity(activity9).event(event5).role(EParticipationRole.RECONOCIMIENTO).date(LocalDate.of(2025, 9, 19)).build());
        System.out.println("Constancias creadas");
    }
}
