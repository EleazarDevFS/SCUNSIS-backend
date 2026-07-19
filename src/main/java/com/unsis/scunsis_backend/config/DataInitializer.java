package com.unsis.scunsis_backend.config;

import com.unsis.scunsis_backend.constants.Constant;
import com.unsis.scunsis_backend.model.activity.Activity;
import com.unsis.scunsis_backend.model.auth.User;
import com.unsis.scunsis_backend.model.enums.EEventType;
import com.unsis.scunsis_backend.model.enums.ERole;
import com.unsis.scunsis_backend.model.event.Event;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.model.sender.Sender;
import com.unsis.scunsis_backend.repository.activity.IActivityRepository;
import com.unsis.scunsis_backend.repository.auth.IUserRepository;
import com.unsis.scunsis_backend.repository.event.IEventRepository;
import com.unsis.scunsis_backend.repository.receiver.IReceiverRepository;
import com.unsis.scunsis_backend.repository.sender.ISenderRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DataInitializer.class);

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ISenderRepository senderRepository;
    private final IReceiverRepository receiverRepository;
    private final IEventRepository eventRepository;
    private final IActivityRepository activityRepository;

    
    private Event event1; 
    private Event event2; 
    private Event event3;
    private Event event4;
    private Event event5;


    @Value("${app.seed.admin-password}")
    private String adminPassword;

    @Value("${app.seed.capturista-password}")
    private String capturistaPassword;

    @Override
    public void run(String... args) {
        seedUsers();
        seedSenders();
        seedReceivers();
        seedEvents();
        seedActivities();
    }

    private void seedUsers() {
        if (userRepository.existsByUsername(Constant.ADMIN_USERNAME)) return;
        userRepository.save(User.builder()
                .username(Constant.ADMIN_USERNAME)
                .password(passwordEncoder.encode(adminPassword))
                .role(ERole.ADMIN)
                .build());
        if (userRepository.existsByUsername(Constant.CAPTURISTA_USERNAME)) return;
        userRepository.save(User.builder()
                .username(Constant.CAPTURISTA_USERNAME)
                .password(passwordEncoder.encode(capturistaPassword))
                .role(ERole.CAPTURISTA)
                .build());
    }

    private void seedSenders() {
        if (senderRepository.count() > 0) return;
        senderRepository.save(Sender.builder().name("Licenciatura en Informatica").campus(Constant.CAMPUS_CENTRAL).build());
        senderRepository.save(Sender.builder().name("Coordinacion de Posgrado").campus(Constant.CAMPUS_CENTRAL).build());
        senderRepository.save(Sender.builder().name("Instituto de Investigacion").campus("Campus Sur").build());
        senderRepository.save(Sender.builder().name("Departamento de Extension Universitaria").campus(Constant.CAMPUS_CENTRAL).build());
        LOG.info("Emisores creados");
    }

    private void seedReceivers() {
        if (receiverRepository.count() > 0) return;
        receiverRepository.save(Receiver.builder().name("Juan").lastName("Perez").twoLastName("Garcia").phone("5551234567").email("juan.perez@example.com").academicGrade(Constant.DEGREE).build());
        receiverRepository.save(Receiver.builder().name("Maria").lastName("Lopez").twoLastName("Martinez").phone("5559876543").email("maria.lopez@example.com").academicGrade("Maestria").build());
        receiverRepository.save(Receiver.builder().name("Carlos").lastName("Hernandez").twoLastName("Rodriguez").phone("5555551234").email("carlos.hernandez@example.com").academicGrade("Doctorado").build());
        receiverRepository.save(Receiver.builder().name("Ana").lastName("Gonzalez").twoLastName("Sanchez").phone("5554443322").email("ana.gonzalez@example.com").academicGrade(Constant.DEGREE).build());
        receiverRepository.save(Receiver.builder().name("Luis").lastName("Ramirez").twoLastName("Torres").phone("5556667788").email("luis.ramirez@example.com").academicGrade("Ingenieria").build());
        receiverRepository.save(Receiver.builder().name("Sofia").lastName("Flores").twoLastName("Cruz").phone("5557778899").email("sofia.flores@example.com").academicGrade("Maestria").build());
        receiverRepository.save(Receiver.builder().name("Pedro").lastName("Morales").twoLastName("Vega").phone("5558889900").email("pedro.morales@example.com").academicGrade(Constant.DEGREE).build());
        receiverRepository.save(Receiver.builder().name("Laura").lastName("Jimenez").twoLastName("Mendoza").phone("5559990011").email("laura.jimenez@example.com").academicGrade("Especialidad").build());
        LOG.info("Receptores creados");
    }

    private void seedEvents() {
        if (eventRepository.count() > 0) return;
        event1 = eventRepository.save(Event.builder().eventType(EEventType.FISICO).eventName("Congreso Internacional de Ingenieria de Software").startDate(LocalDate.of(2025, Month.MARCH, 15)).endDate(LocalDate.of(2025, Month.MARCH, 17)).eventPlace("Auditorio Principal, Campus Central").eventDescription("Compartir avances en el desarrollo de software y metodologias agiles").build());
        event2 = eventRepository.save(Event.builder().eventType(EEventType.VIRTUAL).eventName("Jornadas de Inteligencia Artificial").startDate(LocalDate.of(2025, Month.MAY, 20)).endDate(LocalDate.of(2025, Month.MAY, 22)).eventPlace("Plataforma Zoom").eventDescription("Difundir conocimientos sobre IA y Machine Learning").build());
        event3 = eventRepository.save(Event.builder().eventType(EEventType.FISICO).eventName("Simposio de Ciberseguridad").startDate(LocalDate.of(2025, Month.JUNE, 10)).endDate(LocalDate.of(2025, Month.JUNE, 11)).eventPlace("Centro de Convenciones").eventDescription("Abordar temas de seguridad informatica y proteccion de datos").build());
        event4 = eventRepository.save(Event.builder().eventType(EEventType.VIRTUAL).eventName("Conferencia de Desarrollo Web Moderno").startDate(LocalDate.of(2025, Month.AUGUST, 5)).endDate(LocalDate.of(2025, Month.AUGUST, 6)).eventPlace("Google Meet").eventDescription("Explorar frameworks y tecnologias web actuales").build());
        event5 = eventRepository.save(Event.builder().eventType(EEventType.FISICO).eventName("Foro de Emprendimiento Tecnologico").startDate(LocalDate.of(2025, Month.SEPTEMBER, 18)).endDate(LocalDate.of(2025, Month.SEPTEMBER, 19)).eventPlace("Sala de Usos Multiples").eventDescription("Promover el emprendimiento e innovacion tecnologica").build());
        LOG.info("Eventos creados");
    }

    private void seedActivities() {
        if (activityRepository.count() > 0) return;
        activityRepository.save(Activity.builder().event(event1).activityName("Ponencia: Metodologias Agiles").activityDescription("Presentar las mejores practicas en Scrum y Kanban").startDate(LocalDate.of(2025, Month.MARCH, 15)).endDate(LocalDate.of(2025, Month.MARCH, 15)).activityPlace("Sala A").build());
        activityRepository.save(Activity.builder().event(event1).activityName("Taller: Git Avanzado").activityDescription("Ensenar tecnicas avanzadas de control de versiones").startDate(LocalDate.of(2025, Month.MARCH, 16)).endDate(LocalDate.of(2025, Month.MARCH, 16)).activityPlace("Laboratorio 1").build());
        activityRepository.save(Activity.builder().event(event1).activityName("Panel: Futuro del Software").activityDescription("Discutir tendencias y desafios de la ingenieria de software").startDate(LocalDate.of(2025, Month.MARCH, 17)).endDate(LocalDate.of(2025, Month.MARCH, 17)).activityPlace("Auditorio").build());
        activityRepository.save(Activity.builder().event(event2).activityName("Conferencia: Redes Neuronales").activityDescription("Introduccion a deep learning").startDate(LocalDate.of(2025, Month.MAY, 20)).endDate(LocalDate.of(2025, Month.MAY, 20)).activityPlace("Plataforma Virtual").build());
        activityRepository.save(Activity.builder().event(event2).activityName("Taller: Python para IA").activityDescription("Practica con librerias de machine learning").startDate(LocalDate.of(2025, Month.MAY, 21)).endDate(LocalDate.of(2025, Month.MAY, 21)).activityPlace("Plataforma Virtual").build());
        activityRepository.save(Activity.builder().event(event3).activityName("Ponencia: Ethical Hacking").activityDescription("Tecnicas de pentesting etico").startDate(LocalDate.of(2025, Month.JUNE, 10)).endDate(LocalDate.of(2025, Month.JUNE, 10)).activityPlace("Sala B").build());
        activityRepository.save(Activity.builder().event(event3).activityName("Workshop: Seguridad en la Nube").activityDescription("Proteccion de infraestructuras cloud").startDate(LocalDate.of(2025, Month.JUNE, 11)).endDate(LocalDate.of(2025, Month.JUNE, 11)).activityPlace("Sala C").build());
        activityRepository.save(Activity.builder().event(event4).activityName("Conferencia: React y Next.js").activityDescription("Desarrollo de aplicaciones modernas").startDate(LocalDate.of(2025, Month.AUGUST, 5)).endDate(LocalDate.of(2025, Month.AUGUST, 5)).activityPlace("Google Meet").build());
        activityRepository.save(Activity.builder().event(event5).activityName("Panel: Startups Tech").activityDescription("Experiencias de emprendedores exitosos").startDate(LocalDate.of(2025, Month.SEPTEMBER, 18)).endDate(LocalDate.of(2025, Month.SEPTEMBER, 18)).activityPlace("Sala Principal").build());
        LOG.info("Actividades creadas");
    }

}
