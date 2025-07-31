package inge2.com.alquileresMaria.service;


import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Map;

@Service
public class FileStorageService {

    private final Cloudinary cloudinary;

    public FileStorageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String guardarImagen(MultipartFile imagen) {
        if(imagen.isEmpty()){
            throw new IllegalArgumentException("La imagen esta vacia");
        }
        try {
            String nombre = getNombre(imagen);
            Map uploadResult =cloudinary.uploader().upload(imagen.getBytes(), Map.of("public_id", nombre, "overwrite", true,"resource_type", "image"));
            return  uploadResult.get("secure_url").toString();
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

        int ultimoPunto = nombreImagen.lastIndexOf('.');
        if (ultimoPunto >= 0) {
            nombreImagen = nombreImagen.substring(0, ultimoPunto);
        }

        return nombreImagen + "_" + LocalTime.now();
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
