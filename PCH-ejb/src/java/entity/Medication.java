/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author USER
 */
@Entity
public class Medication implements Serializable {

 



    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 64)
    @NotNull
    @Size(min = 1, max = 64)
    private String name;
    
    @Column(nullable = false, length = 64)
    @Size(min = 1, max = 64)
    private String brand;
    
    @Column(nullable = false, length = 64)
    @Size(min = 1, max = 64)
    private String prescription_quantity;
    
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price_per_quantity;
    
    @Column(nullable = false)
    @NotNull
    @Min(0)
    private int quantity_on_hand;
    
    @Column
    private List<String> conflicting_foods;
    
    @Column(length = 128)
    @Size(min = 1, max = 128)
    private String description;
    
    @Column(length = 128)
    @Size(min = 1, max = 128)
    private String url;
    
    @Column(nullable = false)
    @NotNull
    private boolean deleted;
    
    
    @ManyToMany
    private List<Medication> parent_medications;
    
    @ManyToMany(mappedBy = "parent_medications", fetch = FetchType.EAGER)
    private List<Medication> conflicting_medications;
  
    
    public Medication() {
         this.conflicting_medications = new ArrayList<>();
         this.parent_medications = new ArrayList<>();
         this.conflicting_foods = new ArrayList<>();
    }

    public Medication(String name, String brand, String prescription_quantity, BigDecimal price_per_quantity, int quantity_on_hand, List<String> conflicting_foods, String description, String url) {
        
        super();
        this.name = name;
        this.brand = brand;
        this.prescription_quantity = prescription_quantity;
        this.price_per_quantity = price_per_quantity;
        this.quantity_on_hand = quantity_on_hand;
        this.conflicting_foods = conflicting_foods;
        this.description = description;
        this.url = url;
        this.deleted = false;

        
    }

    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrescription_quantity() {
        return prescription_quantity;
    }

    public void setPrescription_quantity(String prescription_quantity) {
        this.prescription_quantity = prescription_quantity;
    }

    public BigDecimal getPrice_per_quantity() {
        return price_per_quantity;
    }

    public void setPrice_per_quantity(BigDecimal price_per_quantity) {
        this.price_per_quantity = price_per_quantity;
    }

    public int getQuantity_on_hand() {
        return quantity_on_hand;
    }

    public void setQuantity_on_hand(int quantity_on_hand) {
        this.quantity_on_hand = quantity_on_hand;
    }

    public List<String> getConflicting_foods() {
        return conflicting_foods;
    }

    public void setConflicting_foods(List<String> conflicting_foods) {
        this.conflicting_foods = conflicting_foods;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Medication> getConflicting_medications() {
        return conflicting_medications;
    }

    public void setConflicting_medications(List<Medication> conflicting_medications) {
        this.conflicting_medications = conflicting_medications;
    }

     /**
     * @return the parent_medications
     */
    public List<Medication> getParent_medications() {
        return parent_medications;
    }

    /**
     * @param parent_medications the parent_medications to set
     */
    public void setParent_medications(List<Medication> parent_medications) {
        this.parent_medications = parent_medications;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medication)) {
            return false;
        }
        Medication other = (Medication) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MedicationEntity[ id=" + id + " ]";
    }

  
}
