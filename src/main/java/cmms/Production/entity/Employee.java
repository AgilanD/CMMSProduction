package cmms.Production.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_code", nullable = false, unique = true, updatable = false)
    private String employeeCode;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plant_id", nullable = false)
    private Plants plant;

    @Column(nullable = false)
    private String designation;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Past(message = "Joining date cannot be a future date")
    @Temporal(TemporalType.DATE)
    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;


    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;





}