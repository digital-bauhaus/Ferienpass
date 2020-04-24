package de.bauhaus.digital.controller;


/**
 * We use these JSON-Views, so we can conditionally hide/allow fields in the REST-API (per method/request).
 * Not that JSON-Views influence the serialization AND deserialization.
 *
 * Since we set spring.jackson.mapper.default-view-inclusion to 'true', all fields that are
 * not explicitly assigned to a view are always (de)serialized.
 *
 * When you assign the JSON-View "Public" to a method, this method will (de)serialize all fields,
 * that have no View assigned or the View "Public". This especially means, that fields of the View "Internal"
 * will be ignored.
 *
 * When you assign the JSON-View "Internal" to a method, this method will (de)serialize all fields,
 * that have no View assigned, the View "Internal" or the View "Public" (since Internal extends Public).
 *
 * see for example: https://www.baeldung.com/jackson-json-view-annotation
 */
public class Views {
    public static class Public {}
    public static class Internal extends Public {}
}
