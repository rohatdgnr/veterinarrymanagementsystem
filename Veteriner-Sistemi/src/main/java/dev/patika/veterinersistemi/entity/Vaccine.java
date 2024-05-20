package dev.patika.veterinersistemi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "vaccines") //vaccines Entitylerimizin bulundugu sınıfımız
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id")
    private Long id;

    @Column(name = "vaccine_name", nullable = false)
    private String name;

    @Column(name = "vaccine_code",nullable = false)
    private String code;

    //@Temporal(TemporalType.DATE)
    @Column(name = "vaccine_protection_start_date")
    private LocalDate protectionStartDate;

    //@Temporal(TemporalType.DATE)
    @Column(name = "vaccine_protection_finish_date")
    private LocalDate protectionFinishDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Override
    public String toString() {
        return "Vaccine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", protectionStartDate=" + protectionStartDate +
                ", protectionFinishDate=" + protectionFinishDate +
                '}';
    }

}
