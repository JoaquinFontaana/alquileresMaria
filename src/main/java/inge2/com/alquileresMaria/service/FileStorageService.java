package inge2.com.alquileresMaria.service;

import ch.qos.logback.classic.Logger;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class FileStorageService {

    @Value("${upload.dir.autos}")
    private String carpetaDestino;

    public String guardarImagen(MultipartFile imagen) {
        if(imagen.isEmpty()){
            throw new IllegalArgumentException("La imagen esta vacia");
        }
        try {
            String nombreImagen = imagen.getOriginalFilename();
            if (nombreImagen == null){
                throw new IllegalArgumentException("La imagen debe tener nombre");
            }
            Path uploadPath = Paths.get(carpetaDestino).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            imagen.transferTo(uploadPath.resolve(nombreImagen).toFile());
            return uploadPath.resolve(nombreImagen).toString();
        }
        catch (IOException ex){
            throw  new UncheckedIOException("Ocurrio un error al cargar la imagen",ex);
        }
    }
    public String leerImagenComoBase64(String rutaAbsoluta) {
        try {
            Path ruta = Paths.get(rutaAbsoluta).normalize();
            if (!Files.exists(ruta) || Files.isDirectory(ruta)) {
                return null; // O lanzar excepción, según tu criterio
            }
            // Detecta el tipo MIME (p.ej. "image/jpeg")
            String mimeType = Files.probeContentType(ruta);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            byte[] bytes = Files.readAllBytes(ruta);
            String base64 = Base64.getEncoder().encodeToString(bytes);

            return "data:" + mimeType + ";base64," + base64;
        } catch (IOException ex) {
            // Podrías loguear o relanzar RuntimeException según tu política
            throw new UncheckedIOException("Error leyendo o convertiendo la imagen a Base64: " + rutaAbsoluta, ex);
        }
    }
    public void borrarArchivoSiExiste(String rutaAbsoluta) {
        if (rutaAbsoluta == null || rutaAbsoluta.isBlank()) {
            return;
        }
        try {
            Path ruta = Paths.get(rutaAbsoluta).normalize();
            Files.deleteIfExists(ruta);
        } catch (IOException ignored) {
        }
    }
}
