package com.unsis.scunsis_backend.constants;

public class Constant { // NOSONAR S1118
    public static final String NOT_FOUND_BY_ID = "No existe registro en la base de datos con este id";
    public static final String MAIL_FOUND = "Este correo ya esta registrado";
    public static final String NOT_FOUND_SENDER = "Remitente no encontrado";
    public static final String SENDER_EXISTS = "Este remitente ya esta registrado";

    // DataInitializer
    public static final String ADMIN_USERNAME = "admin";
    public static final String CAPTURISTA_USERNAME = "capturista";
    public static final String CAMPUS_CENTRAL = "Campus Central";
    public static final String DEGREE = "Licenciatura";

    // Controller
    // AuthController
    public static final String SUCCESS = "success";
    public static final String MESSAGE = "message";

    // UserController
    public static final String ERROR = "error";

    // Services
    // ProofService
    public static final String PROOF_NOT_FOUND = "Constancia no encontrada con folio: ";
    public static final String SENDER_NOT_FOUND = "Emisor no encontrado";
    public static final String ACTIVITY_NOT_FOUND = "Actividad no encontrada";
    public static final String EVENT_NOT_FOUND = "Evento no encontrado";
}
