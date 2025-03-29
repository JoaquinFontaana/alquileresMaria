package inge2.com.alquileresMaria.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Cliente extends Usuario{
    private int edad;

}
