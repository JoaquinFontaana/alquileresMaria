package inge2.com.alquileresMaria.service;


import inge2.com.alquileresMaria.dto.AutoDTOCrear;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
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
            String nombre = getNombre(imagen);

            //Crear carpetas si no existen
            Path uploadPath = Paths.get(carpetaDestino).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);


            Path filePath = uploadPath.resolve(nombre);

            imagen.transferTo(filePath.toFile());
            return uploadPath.resolve(nombre).toString();
        }
        catch (IOException ex){
            throw  new UncheckedIOException("Ocurrio un error al cargar la imagen",ex);
        }
    }

    private static String getNombre(MultipartFile imagen) {
        String nombreImagen = imagen.getOriginalFilename();
        if (nombreImagen == null){
            throw new IllegalArgumentException("La imagen debe tener nombre");
        }

        String extension = "";
        int ultimoPunto = nombreImagen.lastIndexOf('.');
        if (ultimoPunto >= 0) {
            extension = nombreImagen.substring(ultimoPunto);
            nombreImagen = nombreImagen.substring(0, ultimoPunto);
        }

        return nombreImagen + "_" + LocalTime.now() + extension;
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
    public byte[] leerImagenComoBytes(String ruta) {
        try {
            Path path = Paths.get(ruta).toAbsolutePath().normalize();
            if(!Files.exists(path)){
                throw new RuntimeException("La imagen del auto no existe: " + ruta);
            }
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new UncheckedIOException("Error al leer la imagen", e);
        }
    }
    public void checkImagen(MultipartFile imagen) {
        if (imagen == null || imagen.isEmpty()) {
            throw new IllegalArgumentException("Para crear un auto la imagen es obligatoria");
        }
    }
}
