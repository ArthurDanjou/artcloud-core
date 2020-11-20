package fr.arthurdanjou.artcloud.common.packets;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseStatus {

    SUCCESS(200, "Success"),
    INVALID_REQUEST(400, "Invalid request"),
    NOT_FOUND(404, "Not found"),

    NO_MEMORY(500, "No memory available"),
    NO_CLIENT(500, "No client available"),
    TEMPLATE_ERROR(500, "A template already has that name"),

    ;

    private final int code;
    private final String reason;
}
