/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import entity.Employee;
import entity.MedicalRecord;
import entity.Medication;
import entity.Patient;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.RoleEnum;

/**
 *
 * @author USER
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private AppointmentSessionBeanLocal appointmentSessionBeanLocal;

    @PersistenceContext(unitName = "PCH-ejbPU")
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
        if (em.find(Medication.class, 1l) == null) {

            try {
                em.persist(new Medication("Ampicillin", "Omnipen", "25mg", new BigDecimal(10), 20,
                        new ArrayList<>(Arrays.asList("Coffee", "Seafood")),
                        new ArrayList<>(Arrays.asList("Ampicillin")), "To prevent or treat infections", null));

                em.persist(new Medication("Amoxicillin", "Amoxil", "250mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol", "Coffee")),
                        new ArrayList<>(Arrays.asList("Amoxicillin")), "To prevent or treat infection", null));

                em.persist(new Medication("Baclofen", "Lioresal", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Baclofen")), "Muscle Relaxer, prevent muscle spasm", null));

                em.persist(new Medication("Acetaminophen", "Actamin", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Acetaminophen")), "Pain Reliever", null));

                em.persist(new Medication("Adderall", "Dexedrine", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Fruit juice", "Vitamin C")),
                        new ArrayList<>(Arrays.asList("Amphetamine", "Dextroamphetamine", "Adderall")), "Treat attention deficit hyperactivity disorder and narcolepsy", null));

                em.persist(new Medication("Amitriptyline", "Elavil", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Amitriptyline")), "Antidepressant with sedative effect", null));

                em.persist(new Medication("Amlodipine", "Norvasc", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Seafood")),
                        new ArrayList<>(Arrays.asList("Amlodipine")), "Treat chest pain and High Blood pressure", null));

                em.persist(new Medication("Ativan", "Loraepam", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Ativan")), "Treat anxiety disorder", null));

                em.persist(new Medication("Atorvastatin", "Lipitor", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("High Cholesterol Food ", "Fatty Foods")),
                        new ArrayList<>(Arrays.asList("Atorvastatin")), "Treat high cholesterol, and to lower the risk of stroke", null));

                em.persist(new Medication("Azithromycin ", "azithromycin ", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Spicy food")),
                        new ArrayList<>(Arrays.asList("Azithromycin")), "Treat many different types of infections caused by bacteria", null));

                em.persist(new Medication("Benzonatate", "Tessalon", "100mg", new BigDecimal(10), 20,
                        new ArrayList<>(Arrays.asList("Spicy food")),
                        new ArrayList<>(Arrays.asList("Benzonatate")), "Suppressing cough in acute respiratory conditions such as pneumonia, bronchitis", null));

                em.persist(new Medication("Brilinta", "Ticagrelor", "250mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Amoxicillin", "Brilinta")), "To prevents platelets in your blood from sticking together", null));

                em.persist(new Medication("Bunavail", "Bunavail", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Bunavail")), "Treat opioid addiction", null));

                em.persist(new Medication("Buprenorphine", "Subutex", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Buprenorphine")), "Treat opioid addiction", null));

                em.persist(new Medication("Cephalexin", "Keflex", "500mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Spicy food")),
                        new ArrayList<>(Arrays.asList("Cephalexin")), "Treat infections caused by bacteria", null));

                em.persist(new Medication("Ciprofloxacin", "Cipro", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Milk", "Yogurt")),
                        new ArrayList<>(Arrays.asList("Ciprofloxacin")), " Treat different types of bacterial infection", null));

                em.persist(new Medication("Citalopram ", "CeleXA", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Citalopram")), "Treat depression", null));

                em.persist(new Medication("Clindamycin", "Cleocin", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol", "Milk", "Yogurt")),
                        new ArrayList<>(Arrays.asList("Clindamycin")), "Treat serious infection caused by bacteria", null));

                em.persist(new Medication("Clonazepam", "KlonoPIN", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol ", "Fatty Foods")),
                        new ArrayList<>(Arrays.asList("Clonazepam")), "Treat certain seizure and panic disorders", null));

                em.persist(new Medication("Cyclobenzaprine", "Amrix ", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Cyclobenzaprine")), "Muscle relaxant", null));

                em.persist(new Medication("Cymbalta", "Cymbalta", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Cymbalta")), "Treat major depressive disorder ", null));

                em.persist(new Medication("Doxycycline", "Acticlate", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Seafood", "Banana")),
                        new ArrayList<>(Arrays.asList("Doxycycline")), "Treat moderate-to-severe eczema ", null));

                em.persist(new Medication("Dupixent", "Dupixent", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Milk", "Yogurt")),
                        new ArrayList<>(Arrays.asList("Dupixent")), "Treat moderate-to-severe eczema ", null));

                em.persist(new Medication("Entresto", "Entresto", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Entresto")), "Treatcertain people with chronic heart failure", null));

                em.persist(new Medication("Entyvio", "Entyvio", "500mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Spicy food")),
                        new ArrayList<>(Arrays.asList("Entyvio")), "Treat moderate to severe ulcerative colitis", null));

                em.persist(new Medication("Farxiga", "Farxiga", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Milk", "Yogurt")),
                        new ArrayList<>(Arrays.asList("Farxiga")), " Used together with diet and exercise to improve blood sugar control ", null));

                em.persist(new Medication("Gabapentin ", "Gralise", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Antacid", "Alcohol")),
                        new ArrayList<>(Arrays.asList("Gabapentin")), "Treat partial seizures ", null));

                em.persist(new Medication("Gilenya ", "Gilenya", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Seafood")),
                        new ArrayList<>(Arrays.asList("Gilenya")), "Treat relapsing multiple sclerosis", null));

                em.persist(new Medication("Humira", "Humira", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Clonazepam")), "Treat many inflammatory conditions", null));

                em.persist(new Medication("Hydrochlorothiazide", "HydroDIURIL", "12.5mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Hydrochlorothiazide")), "Treat high blood pressure ", null));

                em.persist(new Medication("Hydroxychloroquine", "Plaquenil", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Antacid", "Alcohol")),
                        new ArrayList<>(Arrays.asList("Hydroxychloroquine")), "Treat symptoms of rheumatoid arthritis and discoid", null));

                em.persist(new Medication("Ibuprofen", "Advil", "600mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Ibuprofen")), "Reduce fever and treat pain or inflammation", null));

                em.persist(new Medication("Imbruvica", "Imbruvica", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Grapefruit", "Orange")),
                        new ArrayList<>(Arrays.asList("Imbruvica")), "Is a cancer medicine that interferes with the growth and spread of cancer cells in the body.", null));

                em.persist(new Medication("Januvia", "Januvia", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Januvia")), "Oral diabetes medicine that helps control blood sugar levels", null));

                em.persist(new Medication("Jardiance", "Jardiance", "50mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Spicy food")),
                        new ArrayList<>(Arrays.asList("Jardiance")), "Oral diabetes medicine that helps control blood sugar level", null));

                em.persist(new Medication("Kevzara", "Kevzara", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Kevzara")), "Treat moderate to severe rheumatoid arthritis", null));

                em.persist(new Medication("Lexapro", "Lexapro", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Lexapro")), "Treat major depressive disorder", null));

                em.persist(new Medication("Lisinopril", "Prinivil", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Seafood", "Milk", "Yogurt")),
                        new ArrayList<>(Arrays.asList("Lisinopril")), "Treat congestive heart failure", null));

                em.persist(new Medication("Lofexidine", "Lucemyra", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Lofexidine")), "Treat opioid withdrawal", null));

                em.persist(new Medication("Loratadine", "Alavert", "12.5mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol", "Spicy food", "Milk", "Cheese", "Yogurt")),
                        new ArrayList<>(Arrays.asList("Loratadine")), "Treat skin hives and itching", null));

                em.persist(new Medication("Lyrica", "Lyrica", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Lyrica")), "Treat nerve pain in people with diabetes", null));

                em.persist(new Medication("Melatonin", "Melatonin", "600mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol", "Coffee", "Tea", "Sweet drinks")),
                        new ArrayList<>(Arrays.asList("Melatonin")), "Treat sleep disorders", null));

                em.persist(new Medication("Meloxicam", "Anjeso", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Meloxicam")), "Treat juvenile rheumatoid arthritis.", null));

                em.persist(new Medication("Metformin", "Fortamet", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Metformin")), "Control blood sugar levels", null));

                em.persist(new Medication("Methadone", "Dolophine", "50mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Grapefruit")),
                        new ArrayList<>(Arrays.asList("Methadone")), "Treat narcotic withdrawal symptoms", null));

                em.persist(new Medication("Methotrexate", "Trexall", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Methotrexate")), "Treat leukemia", null));

                em.persist(new Medication("Metoprolol", "Lopressor", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol", "Sweet drinks")),
                        new ArrayList<>(Arrays.asList("Metoprolol")), "Treat chest pain", null));

                em.persist(new Medication("Naproxen", "Aleve", "10mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Naproxen")), "Treat pain or inflammation caused by conditions such as arthritis", null));

                em.persist(new Medication("Omeprazole ", "Zegerid", "20mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Milk", "Yogurt")),
                        new ArrayList<>(Arrays.asList("Omeprazole")), "Treat symptoms of gastroesophageal reflux disease", null));

                em.persist(new Medication("Onpattro", "Onpattro", "12.5mg", new BigDecimal(10), 10,
                        new ArrayList<>(Arrays.asList("Alcohol")),
                        new ArrayList<>(Arrays.asList("Onpattro")), "Treat polyneuropathy (damage of multiple nerves throughout the body)", null));

                Employee alice = new Employee("Alice", "alice", "password", "alice@gmail.com", RoleEnum.DOCTOR);
                em.persist(alice);
                Employee bob = new Employee("Bob", "bob", "password", "bob@gmail.com", RoleEnum.DOCTOR);
                em.persist(bob);
                Employee charlie = new Employee("Charlie", "charlie", "password", "charlie@gmail.com", RoleEnum.NURSE);
                em.persist(charlie);

                MedicalRecord desmondRecord = new MedicalRecord("Desmond", "S94626123A", "address1",
                        new SimpleDateFormat("dd/MM/yyyy").parse("20/6/1996"), "82746726", "B+",
                        new ArrayList<>(Arrays.asList("Ibuprofen", "Drug 1", "Drug 2", "Drug 3")),
                        new ArrayList<>(Arrays.asList("Family History 1", "Family History 2", "Family History 3")),
                        new ArrayList<>(Arrays.asList("Past History 1", "Past History 2", "Past History 3")),
                        new ArrayList<>(Arrays.asList("Vaccination 1", "Vaccination 2", "Vaccination 3")));
                Patient desmond = new Patient("desmond", "password", "desmond@gmail.com", desmondRecord);
                em.persist(desmond);

                MedicalRecord ericRecord = new MedicalRecord("Eric", "S92726521F", "address1",
                        new SimpleDateFormat("dd/MM/yyyy").parse("20/6/1996"), "83873682", "O",
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                Patient eric = new Patient("eric", "password", "eric@gmail.com", ericRecord);
                em.persist(eric);

            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

}
