/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AppointmentSessionBeanLocal;
import entity.Appointment;
import entity.Employee;
import entity.MedicalRecord;
import entity.Medication;
import entity.Patient;
import entity.QueueBoardItem;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.AppointmentTypeEnum;
import util.enumeration.RoleEnum;
import util.enumeration.ScheduleTypeEnum;
import util.enumeration.StatusEnum;
import util.exception.AppointmentEntityException;

/**
 *
 * @author USER
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private QueueBoardSessionBeanLocal queueBoardSessionBeanLocal;

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

                Patient desmond = new Patient("desmond", "password", "desmond@gmail.com", desmondRecord); em.persist(desmond);
                
                MedicalRecord ericRecord = new MedicalRecord("Eric", "S92726521F", "address1", 
                        new SimpleDateFormat("dd/MM/yyyy").parse("20/6/1996"), "83873682", "O", 
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()); 
                Patient eric = new Patient("eric", "password", "eric@gmail.com", ericRecord); em.persist(eric);
                

                generateAppointments();
                if (em.find(MedicalRecord.class, 3l) == null) {
                    dataInitMedicalRecords();
                }

            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }

        }
                
        generateQueueBoard();
    }
    
    private void generateQueueBoard() {
        Query query = em.createQuery("SELECT a FROM Appointment a WHERE a.status IN (?1,?2) ORDER BY a.date_time DESC");
        query.setParameter(1, StatusEnum.COMPLETED);
        query.setParameter(2, StatusEnum.MISSED);
        query.setMaxResults(100);
        List<Appointment> appointments = query.getResultList();
        
        List<QueueBoardItem> qb = new ArrayList<>();
        for (Appointment a : appointments) qb.add(new QueueBoardItem(a.getEmployee(), a));
        
        queueBoardSessionBeanLocal.setQueueBoard(qb);
    }
    
    private void dataInitMedicalRecords() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            MedicalRecord AlexRecord = new MedicalRecord("Alex Russo", "F3684601O", "Briarwood Drive,123,Singapore", format.parse("2007-12-23"), "89089690", "A+", new ArrayList<>(Arrays.asList("Entyvio", "Azithromycin")), new ArrayList<>(Arrays.asList("Mattticular syndrome", "Cardiomyopathy", "IgG4-related sclerosing disease", "Asthma", "Pneumococcal infection")), new ArrayList<>(Arrays.asList("Mattticular syndrome", "Pneumococcal infection", "Marburg fever", "Trichinosis", "Asthma", "Heart disease")), new ArrayList<>(Arrays.asList("Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)")));
            em.persist(AlexRecord);
            Patient Alex = new Patient("Alex", "password", "Alex@hotmail.com", AlexRecord);
            em.persist(Alex);
            MedicalRecord StarrRecord = new MedicalRecord("Starr Carlton", "G2185524O", "Route 1,123,Singapore", format.parse("1976-01-24"), "91275391", "A-", new ArrayList<>(Arrays.asList("Ciprofloxacin", "Brilinta")), new ArrayList<>(Arrays.asList("Asthma", "Hypogammaglobulinemia", "Strabismus", "Necrotizing Fasciitis")), new ArrayList<>(Arrays.asList("IgG4-related sclerosing disease")), new ArrayList<>(Arrays.asList("Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)")));
            em.persist(StarrRecord);
            Patient Starr = new Patient("Starr", "password", "Starr@yahoo.com", StarrRecord);
            em.persist(Starr);
            MedicalRecord KimberlyRecord = new MedicalRecord("Kimberly Castillo", "S4830296O", "Summit Avenue,123,Singapore", format.parse("1969-08-01"), "89768952", "A-", new ArrayList<>(Arrays.asList("Azithromycin", "Citalopram", "Entyvio")), new ArrayList<>(Arrays.asList("Jaundice", "Chronic inflammatory demyelinating polyneuropathy")), new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Varicella (VAR)", "Inactivated poliovirus (IPV)", "Haemophilus influenzae type b (Hib)", "Bacillus Calmette-Guérin (BCG)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Pneumococcal polysaccharide (PPSV23)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)")));
            em.persist(KimberlyRecord);
            Patient Kimberly = new Patient("Kimberly", "password", "Kimberly@outlook.com", KimberlyRecord);
            em.persist(Kimberly);
            MedicalRecord BettinaRecord = new MedicalRecord("Bettina Yaple", "S7713476M", "Ann Street,123,Singapore", format.parse("2014-09-19"), "81475228", "A+", new ArrayList<>(Arrays.asList("Benzonatate")), new ArrayList<>(Arrays.asList("CREST disease", "Trichinosis")), new ArrayList<>(Arrays.asList("Mattticular syndrome", "Pilia")), new ArrayList<>(Arrays.asList("Pneumococcal polysaccharide (PPSV23)", "Haemophilus influenzae type b (Hib)", "Influenza (INF)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Measles, mumps and rubella (MMR)", "Inactivated poliovirus (IPV)", "Hepatitis B (HepB)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Human papillomavirus (HPV2 or HPV4)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Varicella (VAR)")));
            em.persist(BettinaRecord);
            Patient Bettina = new Patient("Bettina", "password", "Bettina@gmail.com", BettinaRecord);
            em.persist(Bettina);
            MedicalRecord QueenieRecord = new MedicalRecord("Queenie Finnen", "G6635267A", "White Street,123,Singapore", format.parse("1969-07-27"), "95752761", "O+", new ArrayList<>(Arrays.asList("Farxiga")), new ArrayList<>(Arrays.asList("Porphyria", "Hemolytic-uremic syndrome", "Necrotizing Fasciitis", "Trichinosis")), new ArrayList<>(Arrays.asList("Asthma", "Pilia", "Dermatomyositis")), new ArrayList<>(Arrays.asList("Inactivated poliovirus (IPV)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Pneumococcal polysaccharide (PPSV23)", "Varicella (VAR)", "Haemophilus influenzae type b (Hib)", "Hepatitis B (HepB)", "Human papillomavirus (HPV2 or HPV4)", "Influenza (INF)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)")));
            em.persist(QueenieRecord);
            Patient Queenie = new Patient("Queenie", "password", "Queenie@yahoo.com", QueenieRecord);
            em.persist(Queenie);
            MedicalRecord KimberleeRecord = new MedicalRecord("Kimberlee Pazos", "T1893299P", "Fairway Drive,123,Singapore", format.parse("1967-09-24"), "88508331", "B-", new ArrayList<>(Arrays.asList("Doxycycline", "Cyclobenzaprine", "Atorvastatin")), new ArrayList<>(Arrays.asList("Pneumococcal infection")), new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Hepatitis B (HepB)")));
            em.persist(KimberleeRecord);
            Patient Kimberlee = new Patient("Kimberlee", "password", "Kimberlee@yahoo.com", KimberleeRecord);
            em.persist(Kimberlee);
            MedicalRecord SeemaRecord = new MedicalRecord("Seema Nakamura", "S8254615J", "John Street,123,Singapore", format.parse("1948-12-05"), "84665096", "O+", new ArrayList<>(Arrays.asList("Ativan")), new ArrayList<>(Arrays.asList("Hemolytic-uremic syndrome", "Pilia")), new ArrayList<>(Arrays.asList("IgG4-related sclerosing disease", "Necrotizing Fasciitis", "Trichinosis")), new ArrayList<>(Arrays.asList("Haemophilus influenzae type b (Hib)", "Human papillomavirus (HPV2 or HPV4)", "Hepatitis B (HepB)", "Varicella (VAR)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Inactivated poliovirus (IPV)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Pneumococcal polysaccharide (PPSV23)")));
            em.persist(SeemaRecord);
            Patient Seema = new Patient("Seema", "password", "Seema@hotmail.com", SeemaRecord);
            em.persist(Seema);
            MedicalRecord DominiqueRecord = new MedicalRecord("Dominique Andersen", "F9285461U", "Laurel Drive,123,Singapore", format.parse("2016-07-16"), "87979606", "A+", new ArrayList<>(Arrays.asList("Ciprofloxacin")), new ArrayList<>(Arrays.asList("Pure red cell aplasia", "Trichinosis", "Chronic inflammatory demyelinating polyneuropathy", "Porphyria")), new ArrayList<>(Arrays.asList("Pure red cell aplasia", "Pilia", "Necrotizing Fasciitis", "Chronic inflammatory demyelinating polyneuropathy", "Asthma")), new ArrayList<>(Arrays.asList("Haemophilus influenzae type b (Hib)", "Measles, mumps and rubella (MMR)", "Influenza (INF)", "Pneumococcal polysaccharide (PPSV23)", "Human papillomavirus (HPV2 or HPV4)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Hepatitis B (HepB)")));
            em.persist(DominiqueRecord);
            Patient Dominique = new Patient("Dominique", "password", "Dominique@gmail.com", DominiqueRecord);
            em.persist(Dominique);
            MedicalRecord KatheyRecord = new MedicalRecord("Kathey Bucci", "T8410230Q", "Cedar Lane,123,Singapore", format.parse("1987-05-30"), "85433092", "A+", new ArrayList<>(Arrays.asList("Azithromycin", "Dupixent", "Fentanyl")), new ArrayList<>(Arrays.asList("Trichinosis", "Pneumococcal infection", "Jaundice", "Asthma", "Pilia")), new ArrayList<>(Arrays.asList("Asthma", "Trichinosis", "Pneumococcal infection", "Marburg fever", "Porphyria")), new ArrayList<>(Arrays.asList("Pneumococcal conjugate  (PCV10 or PCV13)", "Pneumococcal polysaccharide (PPSV23)", "Hepatitis B (HepB)", "Inactivated poliovirus (IPV)", "Influenza (INF)")));
            em.persist(KatheyRecord);
            Patient Kathey = new Patient("Kathey", "password", "Kathey@hotmail.com", KatheyRecord);
            em.persist(Kathey);
            MedicalRecord KindraRecord = new MedicalRecord("Kindra Thiessen", "T4159650R", "Olive Street,123,Singapore", format.parse("1998-02-02"), "83286544", "O+", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Heart disease", "Schmidt syndrome")), new ArrayList<>(Arrays.asList("Mattticular syndrome")), new ArrayList<>(Arrays.asList("Influenza (INF)", "Bacillus Calmette-Guérin (BCG)", "Varicella (VAR)", "Human papillomavirus (HPV2 or HPV4)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Pneumococcal polysaccharide (PPSV23)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)")));
            em.persist(KindraRecord);
            Patient Kindra = new Patient("Kindra", "password", "Kindra@yahoo.com", KindraRecord);
            em.persist(Kindra);
            MedicalRecord ElenoreRecord = new MedicalRecord("Elenore Gerrity", "G5330983M", "Windsor Drive,123,Singapore", format.parse("2001-04-01"), "95677242", "O+", new ArrayList<>(Arrays.asList("Bunavail", "Azithromycin", "Clonazepam")), new ArrayList<>(Arrays.asList("Hemolytic-uremic syndrome", "Trichinosis", "Pilia", "Chronic inflammatory demyelinating polyneuropathy", "Asthma", "Dermatomyositis")), new ArrayList<>(Arrays.asList("Strabismus", "Chronic inflammatory demyelinating polyneuropathy")), new ArrayList<>(Arrays.asList("Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Pneumococcal polysaccharide (PPSV23)", "Inactivated poliovirus (IPV)", "Bacillus Calmette-Guérin (BCG)", "Measles, mumps and rubella (MMR)", "Hepatitis B (HepB)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Pneumococcal conjugate  (PCV10 or PCV13)")));
            em.persist(ElenoreRecord);
            Patient Elenore = new Patient("Elenore", "password", "Elenore@yahoo.com", ElenoreRecord);
            em.persist(Elenore);
            MedicalRecord ScarletRecord = new MedicalRecord("Scarlet Hopwood", "S6102872S", "Garfield Avenue,123,Singapore", format.parse("1950-11-28"), "80760735", "B-", new ArrayList<>(Arrays.asList("Farxiga", "Ativan")), new ArrayList<>(Arrays.asList("Pilia", "IgG4-related sclerosing disease", "Dermatomyositis", "Porphyria")), new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Bacillus Calmette-Guérin (BCG)", "Inactivated poliovirus (IPV)", "Varicella (VAR)", "Human papillomavirus (HPV2 or HPV4)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Haemophilus influenzae type b (Hib)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Influenza (INF)")));
            em.persist(ScarletRecord);
            Patient Scarlet = new Patient("Scarlet", "password", "Scarlet@yahoo.com", ScarletRecord);
            em.persist(Scarlet);
            MedicalRecord KathaleenRecord = new MedicalRecord("Kathaleen Marbury", "F9242008X", "Linden Avenue,123,Singapore", format.parse("1973-10-09"), "98619130", "B+", new ArrayList<>(Arrays.asList("Azithromycin", "Brilinta", "Benzonatate")), new ArrayList<>(Arrays.asList("Cardiomyopathy", "Dermatomyositis", "Marburg fever", "CREST disease", "Hypogammaglobulinemia")), new ArrayList<>(Arrays.asList("Hypogammaglobulinemia", "Strabismus", "Heart disease", "Dermatomyositis")), new ArrayList<>(Arrays.asList("Varicella (VAR)", "Human papillomavirus (HPV2 or HPV4)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Inactivated poliovirus (IPV)", "Hepatitis B (HepB)", "Pneumococcal polysaccharide (PPSV23)", "Haemophilus influenzae type b (Hib)", "Influenza (INF)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)")));
            em.persist(KathaleenRecord);
            Patient Kathaleen = new Patient("Kathaleen", "password", "Kathaleen@hotmail.com", KathaleenRecord);
            em.persist(Kathaleen);
            MedicalRecord TreasaRecord = new MedicalRecord("Treasa Bundy", "S9982828I", "Pleasant Street,123,Singapore", format.parse("1955-09-07"), "82016806", "O-", new ArrayList<>(Arrays.asList("Atorvastatin")), new ArrayList<>(Arrays.asList("Chronic inflammatory demyelinating polyneuropathy", "Strabismus", "Marburg fever")), new ArrayList<>(Arrays.asList("Hemolytic-uremic syndrome", "Jaundice")), new ArrayList<>(Arrays.asList("Inactivated poliovirus (IPV)", "Varicella (VAR)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Measles, mumps and rubella (MMR)", "Haemophilus influenzae type b (Hib)", "Influenza (INF)", "Hepatitis B (HepB)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)")));
            em.persist(TreasaRecord);
            Patient Treasa = new Patient("Treasa", "password", "Treasa@yahoo.com", TreasaRecord);
            em.persist(Treasa);
            MedicalRecord LinaRecord = new MedicalRecord("Lina Hamman", "G5303737M", "Jefferson Avenue,123,Singapore", format.parse("1979-12-14"), "87064912", "B+", new ArrayList<>(Arrays.asList("Citalopram", "Cyclobenzaprine")), new ArrayList<>(Arrays.asList("Marburg fever", "Pure red cell aplasia", "Dermatomyositis", "Chronic inflammatory demyelinating polyneuropathy", "Necrotizing Fasciitis")), new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Pneumococcal polysaccharide (PPSV23)")));
            em.persist(LinaRecord);
            Patient Lina = new Patient("Lina", "password", "Lina@outlook.com", LinaRecord);
            em.persist(Lina);
            MedicalRecord JaniRecord = new MedicalRecord("Jani Kaufman", "G2840499G", "Briarwood Drive,123,Singapore", format.parse("2000-02-17"), "87370440", "O-", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("IgG4-related sclerosing disease", "Porphyria", "Necrotizing Fasciitis", "Pure red cell aplasia", "Dermatomyositis")), new ArrayList<>(Arrays.asList("Cardiomyopathy")), new ArrayList<>(Arrays.asList("Pneumococcal polysaccharide (PPSV23)", "Influenza (INF)", "Hepatitis B (HepB)", "Measles, mumps and rubella (MMR)", "Varicella (VAR)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Inactivated poliovirus (IPV)", "Bacillus Calmette-Guérin (BCG)")));
            em.persist(JaniRecord);
            Patient Jani = new Patient("Jani", "password", "Jani@outlook.com", JaniRecord);
            em.persist(Jani);
            MedicalRecord CelindaRecord = new MedicalRecord("Celinda Hinch", "S1883355N", "Madison Avenue,123,Singapore", format.parse("2009-08-26"), "83148956", "A+", new ArrayList<>(Arrays.asList("Clonazepam", "Buprenorphine")), new ArrayList<>(Arrays.asList("Pneumococcal infection", "IgG4-related sclerosing disease")), new ArrayList<>(Arrays.asList("Schmidt syndrome", "Dermatomyositis", "Pure red cell aplasia", "Porphyria", "CREST disease", "Jaundice")), new ArrayList<>(Arrays.asList("Varicella (VAR)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Human papillomavirus (HPV2 or HPV4)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Pneumococcal polysaccharide (PPSV23)", "Inactivated poliovirus (IPV)")));
            em.persist(CelindaRecord);
            Patient Celinda = new Patient("Celinda", "password", "Celinda@gmail.com", CelindaRecord);
            em.persist(Celinda);
            MedicalRecord RosiaRecord = new MedicalRecord("Rosia Backus", "G2293302L", "Charles Street,123,Singapore", format.parse("1992-10-14"), "94265160", "A-", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Porphyria", "CREST disease", "Asthma")), new ArrayList<>(Arrays.asList("Schmidt syndrome", "Pure red cell aplasia", "Hemolytic-uremic syndrome", "Marburg fever", "Heart disease")), new ArrayList<>(Arrays.asList("Pneumococcal polysaccharide (PPSV23)", "Hepatitis B (HepB)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Influenza (INF)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)")));
            em.persist(RosiaRecord);
            Patient Rosia = new Patient("Rosia", "password", "Rosia@hotmail.com", RosiaRecord);
            em.persist(Rosia);
            MedicalRecord GrantRecord = new MedicalRecord("Grant Padgett", "T6590823X", "Heather Lane,123,Singapore", format.parse("1977-04-16"), "96181362", "B+", new ArrayList<>(Arrays.asList("Buprenorphine", "Cephalexin", "Entyvio")), new ArrayList<>(Arrays.asList("Jaundice", "Cardiomyopathy", "Hemolytic-uremic syndrome", "IgG4-related sclerosing disease", "Pilia", "Asthma")), new ArrayList<>(Arrays.asList("Pure red cell aplasia", "Hypogammaglobulinemia", "Pneumococcal infection")), new ArrayList<>(Arrays.asList("Influenza (INF)", "Inactivated poliovirus (IPV)", "Human papillomavirus (HPV2 or HPV4)")));
            em.persist(GrantRecord);
            Patient Grant = new Patient("Grant", "password", "Grant@gmail.com", GrantRecord);
            em.persist(Grant);
            MedicalRecord VerdaRecord = new MedicalRecord("Verda Angulo", "T1458622S", "Woodland Drive,123,Singapore", format.parse("1960-05-19"), "89513908", "O-", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Trichinosis", "Necrotizing Fasciitis")), new ArrayList<>(Arrays.asList("Hypogammaglobulinemia")), new ArrayList<>(Arrays.asList("Pneumococcal conjugate  (PCV10 or PCV13)", "Human papillomavirus (HPV2 or HPV4)", "Pneumococcal polysaccharide (PPSV23)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Bacillus Calmette-Guérin (BCG)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)")));
            em.persist(VerdaRecord);
            Patient Verda = new Patient("Verda", "password", "Verda@outlook.com", VerdaRecord);
            em.persist(Verda);
            MedicalRecord LaurieRecord = new MedicalRecord("Laurie Hickox", "T9468294O", "Fairway Drive,123,Singapore", format.parse("2009-03-15"), "86658740", "A-", new ArrayList<>(Arrays.asList("Fentanyl")), new ArrayList<>(Arrays.asList("Pure red cell aplasia", "Pneumococcal infection")), new ArrayList<>(Arrays.asList("Porphyria", "Dermatomyositis", "IgG4-related sclerosing disease", "Pilia", "Pneumococcal infection")), new ArrayList<>(Arrays.asList("Human papillomavirus (HPV2 or HPV4)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Influenza (INF)", "Varicella (VAR)", "Bacillus Calmette-Guérin (BCG)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)")));
            em.persist(LaurieRecord);
            Patient Laurie = new Patient("Laurie", "password", "Laurie@yahoo.com", LaurieRecord);
            em.persist(Laurie);
            MedicalRecord LarisaRecord = new MedicalRecord("Larisa Perrine", "F2210995N", "White Street,123,Singapore", format.parse("1965-08-04"), "95527722", "O-", new ArrayList<>(Arrays.asList("Azithromycin")), new ArrayList<>(Arrays.asList("Cardiomyopathy", "Chronic inflammatory demyelinating polyneuropathy", "Asthma", "CREST disease", "Schmidt syndrome")), new ArrayList<>(Arrays.asList("Jaundice", "Asthma", "Pure red cell aplasia", "Trichinosis", "Heart disease")), new ArrayList<>(Arrays.asList("Inactivated poliovirus (IPV)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Varicella (VAR)", "Pneumococcal conjugate  (PCV10 or PCV13)")));
            em.persist(LarisaRecord);
            Patient Larisa = new Patient("Larisa", "password", "Larisa@yahoo.com", LarisaRecord);
            em.persist(Larisa);
            MedicalRecord OrvalRecord = new MedicalRecord("Orval Gormley", "G9002616Z", "Grand Avenue,123,Singapore", format.parse("2010-08-05"), "94983251", "A-", new ArrayList<>(Arrays.asList("Brilinta", "Doxycycline", "Cyclobenzaprine")), new ArrayList<>(Arrays.asList("Cardiomyopathy", "Trichinosis", "Hemolytic-uremic syndrome", "Chronic inflammatory demyelinating polyneuropathy", "Pilia", "Mattticular syndrome")), new ArrayList<>(Arrays.asList("Strabismus", "Cardiomyopathy")), new ArrayList<>(Arrays.asList("Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)")));
            em.persist(OrvalRecord);
            Patient Orval = new Patient("Orval", "password", "Orval@yahoo.com", OrvalRecord);
            em.persist(Orval);
            MedicalRecord SuziRecord = new MedicalRecord("Suzi Camillo", "S5464140Q", "Monroe Street,123,Singapore", format.parse("2010-11-19"), "81047216", "O+", new ArrayList<>(Arrays.asList("Cyclobenzaprine", "Buprenorphine", "Cephalexin")), new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Measles, mumps and rubella (MMR)", "Hepatitis B (HepB)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Pneumococcal polysaccharide (PPSV23)", "Varicella (VAR)", "Human papillomavirus (HPV2 or HPV4)", "Bacillus Calmette-Guérin (BCG)", "Haemophilus influenzae type b (Hib)", "Inactivated poliovirus (IPV)")));
            em.persist(SuziRecord);
            Patient Suzi = new Patient("Suzi", "password", "Suzi@yahoo.com", SuziRecord);
            em.persist(Suzi);
            MedicalRecord EuniceRecord = new MedicalRecord("Eunice Daigneault", "T3605445P", "Madison Avenue,123,Singapore", format.parse("1993-10-28"), "82847404", "A-", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Chronic inflammatory demyelinating polyneuropathy")), new ArrayList<>(Arrays.asList("Dermatomyositis")), new ArrayList<>(Arrays.asList("Bacillus Calmette-Guérin (BCG)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)")));
            em.persist(EuniceRecord);
            Patient Eunice = new Patient("Eunice", "password", "Eunice@outlook.com", EuniceRecord);
            em.persist(Eunice);
            MedicalRecord CorrinneRecord = new MedicalRecord("Corrinne Hirsch", "G3203237X", "Route 1,123,Singapore", format.parse("1978-10-03"), "92045630", "B+", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Mattticular syndrome")), new ArrayList<>(Arrays.asList("Jaundice", "Necrotizing Fasciitis", "Pure red cell aplasia")), new ArrayList<>(Arrays.asList("Influenza (INF)", "Measles, mumps and rubella (MMR)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Human papillomavirus (HPV2 or HPV4)", "Inactivated poliovirus (IPV)")));
            em.persist(CorrinneRecord);
            Patient Corrinne = new Patient("Corrinne", "password", "Corrinne@yahoo.com", CorrinneRecord);
            em.persist(Corrinne);
            MedicalRecord BoyceRecord = new MedicalRecord("Boyce Coutu", "F7901581C", "Andover Court,123,Singapore", format.parse("1987-02-04"), "97682086", "O+", new ArrayList<>(Arrays.asList("Bunavail", "Entresto")), new ArrayList<>(Arrays.asList("Pneumococcal infection", "Chronic inflammatory demyelinating polyneuropathy", "Necrotizing Fasciitis", "Mattticular syndrome")), new ArrayList<>(Arrays.asList("Pure red cell aplasia", "Schmidt syndrome", "Asthma")), new ArrayList<>(Arrays.asList()));
            em.persist(BoyceRecord);
            Patient Boyce = new Patient("Boyce", "password", "Boyce@yahoo.com", BoyceRecord);
            em.persist(Boyce);
            MedicalRecord TrangRecord = new MedicalRecord("Trang Hakes", "F4576297J", "Route 30,123,Singapore", format.parse("1997-07-31"), "96864089", "O-", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Schmidt syndrome", "Jaundice", "Cardiomyopathy", "Pure red cell aplasia")), new ArrayList<>(Arrays.asList("Necrotizing Fasciitis", "CREST disease", "IgG4-related sclerosing disease", "Dermatomyositis", "Hemolytic-uremic syndrome")), new ArrayList<>(Arrays.asList("Pneumococcal polysaccharide (PPSV23)", "Bacillus Calmette-Guérin (BCG)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)")));
            em.persist(TrangRecord);
            Patient Trang = new Patient("Trang", "password", "Trang@yahoo.com", TrangRecord);
            em.persist(Trang);
            MedicalRecord AileenRecord = new MedicalRecord("Aileen Shiflett", "G5376144I", "Woodland Drive,123,Singapore", format.parse("1973-01-04"), "95260789", "B-", new ArrayList<>(Arrays.asList("Cephalexin", "Azithromycin", "Farxiga")), new ArrayList<>(Arrays.asList("CREST disease")), new ArrayList<>(Arrays.asList("Marburg fever", "CREST disease")), new ArrayList<>(Arrays.asList("Haemophilus influenzae type b (Hib)", "Human papillomavirus (HPV2 or HPV4)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Measles, mumps and rubella (MMR)", "Pneumococcal polysaccharide (PPSV23)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)")));
            em.persist(AileenRecord);
            Patient Aileen = new Patient("Aileen", "password", "Aileen@gmail.com", AileenRecord);
            em.persist(Aileen);
            MedicalRecord LatriceRecord = new MedicalRecord("Latrice Sharpe", "F4222121J", "Charles Street,123,Singapore", format.parse("1949-02-27"), "93737792", "O+", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Marburg fever", "Chronic inflammatory demyelinating polyneuropathy", "Cardiomyopathy", "Hemolytic-uremic syndrome", "Mattticular syndrome", "Porphyria")), new ArrayList<>(Arrays.asList("Porphyria", "Cardiomyopathy")), new ArrayList<>(Arrays.asList("Pneumococcal polysaccharide (PPSV23)", "Measles, mumps and rubella (MMR)")));
            em.persist(LatriceRecord);
            Patient Latrice = new Patient("Latrice", "password", "Latrice@hotmail.com", LatriceRecord);
            em.persist(Latrice);
            MedicalRecord ValdaRecord = new MedicalRecord("Valda Shortt", "F7271944F", "Laurel Drive,123,Singapore", format.parse("1946-09-30"), "93726498", "B+", new ArrayList<>(Arrays.asList("Fentanyl", "Dupixent", "Ciprofloxacin")), new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Pure red cell aplasia", "Jaundice", "Marburg fever", "Schmidt syndrome")), new ArrayList<>(Arrays.asList("Hepatitis B (HepB)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Inactivated poliovirus (IPV)", "Haemophilus influenzae type b (Hib)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Measles, mumps and rubella (MMR)", "Varicella (VAR)", "Influenza (INF)")));
            em.persist(ValdaRecord);
            Patient Valda = new Patient("Valda", "password", "Valda@gmail.com", ValdaRecord);
            em.persist(Valda);
            MedicalRecord AlysiaRecord = new MedicalRecord("Alysia Ehrhardt", "S2855773B", "Laurel Drive,123,Singapore", format.parse("2015-05-20"), "98432574", "B-", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Hemolytic-uremic syndrome", "Cardiomyopathy")), new ArrayList<>(Arrays.asList("Marburg fever", "Hypogammaglobulinemia", "Trichinosis", "Strabismus", "IgG4-related sclerosing disease")), new ArrayList<>(Arrays.asList("Haemophilus influenzae type b (Hib)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)")));
            em.persist(AlysiaRecord);
            Patient Alysia = new Patient("Alysia", "password", "Alysia@outlook.com", AlysiaRecord);
            em.persist(Alysia);
            MedicalRecord LawannaRecord = new MedicalRecord("Lawanna Grimsley", "T7574527L", "Crescent Street,123,Singapore", format.parse("1983-12-21"), "81184634", "B-", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Porphyria", "Dermatomyositis", "IgG4-related sclerosing disease", "Heart disease", "CREST disease", "Pneumococcal infection")), new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList()));
            em.persist(LawannaRecord);
            Patient Lawanna = new Patient("Lawanna", "password", "Lawanna@hotmail.com", LawannaRecord);
            em.persist(Lawanna);
            MedicalRecord AmadaRecord = new MedicalRecord("Amada Delillo", "S6966073Z", "Route 30,123,Singapore", format.parse("1995-04-20"), "95871251", "B+", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Cardiomyopathy")), new ArrayList<>(Arrays.asList("Asthma", "Chronic inflammatory demyelinating polyneuropathy", "Hypogammaglobulinemia", "Dermatomyositis", "IgG4-related sclerosing disease", "Cardiomyopathy")), new ArrayList<>(Arrays.asList("Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Hepatitis B (HepB)", "Varicella (VAR)", "Influenza (INF)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Inactivated poliovirus (IPV)", "Measles, mumps and rubella (MMR)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Bacillus Calmette-Guérin (BCG)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Pneumococcal polysaccharide (PPSV23)")));
            em.persist(AmadaRecord);
            Patient Amada = new Patient("Amada", "password", "Amada@gmail.com", AmadaRecord);
            em.persist(Amada);
            MedicalRecord VickiRecord = new MedicalRecord("Vicki Beckler", "F7594921F", "Hanover Court,123,Singapore", format.parse("1949-11-25"), "84193635", "A+", new ArrayList<>(Arrays.asList("Atorvastatin", "Cymbalta", "Ciprofloxacin")), new ArrayList<>(Arrays.asList("Marburg fever", "Dermatomyositis", "Asthma")), new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Pneumococcal polysaccharide (PPSV23)", "Influenza (INF)")));
            em.persist(VickiRecord);
            Patient Vicki = new Patient("Vicki", "password", "Vicki@outlook.com", VickiRecord);
            em.persist(Vicki);
            MedicalRecord TillieRecord = new MedicalRecord("Tillie Mcguigan", "T1801292W", "Olive Street,123,Singapore", format.parse("1987-08-29"), "95994313", "O+", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Schmidt syndrome")), new ArrayList<>(Arrays.asList("Necrotizing Fasciitis", "Heart disease", "Cardiomyopathy")), new ArrayList<>(Arrays.asList("Bacillus Calmette-Guérin (BCG)", "Human papillomavirus (HPV2 or HPV4)", "Haemophilus influenzae type b (Hib)", "Measles, mumps and rubella (MMR)")));
            em.persist(TillieRecord);
            Patient Tillie = new Patient("Tillie", "password", "Tillie@gmail.com", TillieRecord);
            em.persist(Tillie);
            MedicalRecord CindyRecord = new MedicalRecord("Cindy Hamilton", "F1173628Y", "Route 30,123,Singapore", format.parse("1949-08-09"), "94978052", "B-", new ArrayList<>(Arrays.asList("Benzonatate", "Atorvastatin", "Ativan")), new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Necrotizing Fasciitis", "Asthma", "Pneumococcal infection", "Heart disease", "Pure red cell aplasia")), new ArrayList<>(Arrays.asList("Haemophilus influenzae type b (Hib)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Measles, mumps and rubella (MMR)", "Bacillus Calmette-Guérin (BCG)", "Varicella (VAR)", "Hepatitis B (HepB)", "Inactivated poliovirus (IPV)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)")));
            em.persist(CindyRecord);
            Patient Cindy = new Patient("Cindy", "password", "Cindy@hotmail.com", CindyRecord);
            em.persist(Cindy);
            MedicalRecord EvetteRecord = new MedicalRecord("Evette Gin", "G7769576V", "Country Club Road,123,Singapore", format.parse("1998-06-06"), "91817774", "A-", new ArrayList<>(Arrays.asList("Azithromycin", "Bunavail")), new ArrayList<>(Arrays.asList("Jaundice", "Porphyria", "Dermatomyositis", "Pilia", "Strabismus", "Hemolytic-uremic syndrome")), new ArrayList<>(Arrays.asList("Jaundice", "Pneumococcal infection", "Pilia", "Porphyria", "Trichinosis")), new ArrayList<>(Arrays.asList("Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Bacillus Calmette-Guérin (BCG)", "Haemophilus influenzae type b (Hib)", "Inactivated poliovirus (IPV)", "Influenza (INF)", "Human papillomavirus (HPV2 or HPV4)", "Varicella (VAR)", "Hepatitis B (HepB)", "Pneumococcal conjugate  (PCV10 or PCV13)")));
            em.persist(EvetteRecord);
            Patient Evette = new Patient("Evette", "password", "Evette@yahoo.com", EvetteRecord);
            em.persist(Evette);
            MedicalRecord ErickRecord = new MedicalRecord("Erick Dubuque", "G8582574S", "Essex Court,123,Singapore", format.parse("2006-08-22"), "88480377", "A-", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Marburg fever", "Hypogammaglobulinemia")), new ArrayList<>(Arrays.asList("IgG4-related sclerosing disease", "Schmidt syndrome", "Asthma", "CREST disease")), new ArrayList<>(Arrays.asList("Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Measles, mumps and rubella (MMR)", "Influenza (INF)")));
            em.persist(ErickRecord);
            Patient Erick = new Patient("Erick", "password", "Erick@outlook.com", ErickRecord);
            em.persist(Erick);
            MedicalRecord AlidaRecord = new MedicalRecord("Alida Weinert", "T7404054Z", "Monroe Street,123,Singapore", format.parse("1987-08-21"), "95469248", "O-", new ArrayList<>(Arrays.asList("Dupixent", "Benzonatate", "Ativan")), new ArrayList<>(Arrays.asList("Jaundice", "Cardiomyopathy", "Necrotizing Fasciitis", "Strabismus", "Dermatomyositis", "Pilia")), new ArrayList<>(Arrays.asList("Marburg fever")), new ArrayList<>(Arrays.asList("Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Inactivated poliovirus (IPV)", "Influenza (INF)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Bacillus Calmette-Guérin (BCG)")));
            em.persist(AlidaRecord);
            Patient Alida = new Patient("Alida", "password", "Alida@yahoo.com", AlidaRecord);
            em.persist(Alida);
            MedicalRecord PokRecord = new MedicalRecord("Pok Whitaker", "T4714458W", "Essex Court,123,Singapore", format.parse("1967-01-14"), "80578577", "O-", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Dermatomyositis", "Pilia", "Necrotizing Fasciitis")), new ArrayList<>(Arrays.asList("Necrotizing Fasciitis", "Mattticular syndrome", "Strabismus", "Chronic inflammatory demyelinating polyneuropathy")), new ArrayList<>(Arrays.asList("Human papillomavirus (HPV2 or HPV4)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Influenza (INF)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Measles, mumps and rubella (MMR)", "Hepatitis B (HepB)", "Varicella (VAR)")));
            em.persist(PokRecord);
            Patient Pok = new Patient("Pok", "password", "Pok@yahoo.com", PokRecord);
            em.persist(Pok);
            MedicalRecord VedaRecord = new MedicalRecord("Veda Malkin", "T8179663T", "Andover Court,123,Singapore", format.parse("1960-12-23"), "82651517", "B+", new ArrayList<>(Arrays.asList("Entresto", "Bunavail", "Ciprofloxacin")), new ArrayList<>(Arrays.asList("Necrotizing Fasciitis", "Mattticular syndrome", "Jaundice")), new ArrayList<>(Arrays.asList("Cardiomyopathy")), new ArrayList<>(Arrays.asList("Inactivated poliovirus (IPV)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Human papillomavirus (HPV2 or HPV4)", "Influenza (INF)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Hepatitis B (HepB)", "Pneumococcal conjugate  (PCV10 or PCV13)")));
            em.persist(VedaRecord);
            Patient Veda = new Patient("Veda", "password", "Veda@yahoo.com", VedaRecord);
            em.persist(Veda);
            MedicalRecord SharynRecord = new MedicalRecord("Sharyn Maddux", "F5710055A", "Franklin Avenue,123,Singapore", format.parse("1988-01-27"), "93391510", "O+", new ArrayList<>(Arrays.asList("Benzonatate", "Azithromycin", "Entyvio")), new ArrayList<>(Arrays.asList("Schmidt syndrome")), new ArrayList<>(Arrays.asList("Jaundice", "Marburg fever", "Pure red cell aplasia", "Strabismus")), new ArrayList<>(Arrays.asList("Measles, mumps and rubella (MMR)", "Varicella (VAR)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)")));
            em.persist(SharynRecord);
            Patient Sharyn = new Patient("Sharyn", "password", "Sharyn@yahoo.com", SharynRecord);
            em.persist(Sharyn);
            MedicalRecord MernaRecord = new MedicalRecord("Merna Sagers", "T9384547C", "Charles Street,123,Singapore", format.parse("1997-12-08"), "83301206", "A-", new ArrayList<>(Arrays.asList("Farxiga")), new ArrayList<>(Arrays.asList("Trichinosis", "Asthma", "Dermatomyositis", "Cardiomyopathy")), new ArrayList<>(Arrays.asList("Pure red cell aplasia")), new ArrayList<>(Arrays.asList("Haemophilus influenzae type b (Hib)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Hepatitis B (HepB)")));
            em.persist(MernaRecord);
            Patient Merna = new Patient("Merna", "password", "Merna@gmail.com", MernaRecord);
            em.persist(Merna);
            MedicalRecord CletaRecord = new MedicalRecord("Cleta Samples", "F8576404E", "Pleasant Street,123,Singapore", format.parse("1958-08-06"), "84950458", "B-", new ArrayList<>(Arrays.asList("Farxiga", "Cymbalta")), new ArrayList<>(Arrays.asList("Schmidt syndrome", "Hypogammaglobulinemia")), new ArrayList<>(Arrays.asList("Porphyria")), new ArrayList<>(Arrays.asList("Human papillomavirus (HPV2 or HPV4)", "Bacillus Calmette-Guérin (BCG)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)")));
            em.persist(CletaRecord);
            Patient Cleta = new Patient("Cleta", "password", "Cleta@yahoo.com", CletaRecord);
            em.persist(Cleta);
            MedicalRecord DemetraRecord = new MedicalRecord("Demetra Padro", "F9224655R", "Franklin Avenue,123,Singapore", format.parse("1968-07-14"), "88828902", "O+", new ArrayList<>(Arrays.asList("Benzonatate", "Doxycycline", "Bunavail")), new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Asthma", "Dermatomyositis", "CREST disease")), new ArrayList<>(Arrays.asList("Hepatitis B (HepB)", "Measles, mumps and rubella (MMR)")));
            em.persist(DemetraRecord);
            Patient Demetra = new Patient("Demetra", "password", "Demetra@hotmail.com", DemetraRecord);
            em.persist(Demetra);
            MedicalRecord TammyRecord = new MedicalRecord("Tammy Ragsdale", "S5098319M", "Arlington Avenue,123,Singapore", format.parse("1945-01-04"), "86173426", "O+", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Heart disease")), new ArrayList<>(Arrays.asList("Pneumococcal infection", "Cardiomyopathy", "Pilia", "Marburg fever", "Mattticular syndrome")), new ArrayList<>(Arrays.asList("Bacillus Calmette-Guérin (BCG)", "Influenza (INF)", "Pneumococcal polysaccharide (PPSV23)", "Human papillomavirus (HPV2 or HPV4)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Varicella (VAR)", "Hepatitis B (HepB)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)")));
            em.persist(TammyRecord);
            Patient Tammy = new Patient("Tammy", "password", "Tammy@outlook.com", TammyRecord);
            em.persist(Tammy);
            MedicalRecord RodgerRecord = new MedicalRecord("Rodger Crutcher", "G3265639C", "Madison Avenue,123,Singapore", format.parse("1982-11-22"), "94473944", "O+", new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList("Trichinosis", "Marburg fever", "Hemolytic-uremic syndrome")), new ArrayList<>(Arrays.asList("IgG4-related sclerosing disease", "Jaundice", "Chronic inflammatory demyelinating polyneuropathy", "Trichinosis", "Necrotizing Fasciitis", "Asthma")), new ArrayList<>(Arrays.asList("Haemophilus influenzae type b (Hib)", "Pneumococcal polysaccharide (PPSV23)", "Tetanus, reduced diphtheria and acellular pertussis (Tdap)", "Varicella (VAR)", "Pneumococcal conjugate  (PCV10 or PCV13)", "Influenza (INF)", "Measles, mumps and rubella (MMR)", "Bacillus Calmette-Guérin (BCG)", "Diphtheria, tetanus and acellular pertussis (paediatric) (DTaP)", "Human papillomavirus (HPV2 or HPV4)", "Inactivated poliovirus (IPV)")));
            em.persist(RodgerRecord);
            Patient Rodger = new Patient("Rodger", "password", "Rodger@hotmail.com", RodgerRecord);
            em.persist(Rodger);
            MedicalRecord AlaneRecord = new MedicalRecord("Alane Ewers", "S4879870Z", "John Street,123,Singapore", format.parse("2012-03-02"), "97735957", "A+", new ArrayList<>(Arrays.asList("Clindamycin", "Cephalexin")), new ArrayList<>(Arrays.asList("Cardiomyopathy", "Mattticular syndrome", "Dermatomyositis", "Porphyria", "Jaundice", "Heart disease")), new ArrayList<>(Arrays.asList("Hemolytic-uremic syndrome")), new ArrayList<>(Arrays.asList("Inactivated poliovirus (IPV)", "Severe acute respiratory syndrome coronavirus 2 (SARS-CoV-2)", "Varicella (VAR)", "Haemophilus influenzae type b (Hib)", "Pneumococcal polysaccharide (PPSV23)")));
            em.persist(AlaneRecord);
            Patient Alane = new Patient("Alane", "password", "Alane@outlook.com", AlaneRecord);
            em.persist(Alane);
            MedicalRecord TiffanieRecord = new MedicalRecord("Tiffanie Chapple", "S7905996C", "Arlington Avenue,123,Singapore", format.parse("1983-09-10"), "86630288", "O+", new ArrayList<>(Arrays.asList("Buprenorphine", "Azithromycin")), new ArrayList<>(Arrays.asList("Mattticular syndrome", "Porphyria", "Pneumococcal infection", "Hypogammaglobulinemia")), new ArrayList<>(Arrays.asList("Necrotizing Fasciitis", "IgG4-related sclerosing disease")), new ArrayList<>(Arrays.asList("Pneumococcal conjugate  (PCV10 or PCV13)", "Bacillus Calmette-Guérin (BCG)", "Inactivated poliovirus (IPV)", "Hepatitis B (HepB)")));
            em.persist(TiffanieRecord);
            Patient Tiffanie = new Patient("Tiffanie", "password", "Tiffanie@hotmail.com", TiffanieRecord);
            em.persist(Tiffanie);


        } catch (ParseException ex) {
            Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }



    }
    
    private void generateAppointments() {
        
        double p = 0.9; // probability for a time slot to be booked
        int monthsBefore = -1; // how far before to add appointments since
        int monthsAfter = 1; // how far after to add appointments til
        
        double p_consultation = 0.7; // probability of an appointment being a consultation
        double p_checkup = 0.2; // probability of an appointment being a health checkup
        double p_vaccination = 1 - p_consultation - p_checkup; // probability of an appointment being a vaccination
        
        double p_appointment = 0.8;
        double p_walkin = 1 - p_appointment;
        
        double p_completed = 0.8; // probability of past appointments being completed
        double p_cancelled = 0.1; // probability of past appointments being cancelled
        double p_missed = 1 - p_completed - p_cancelled; // probability of past appointments being missed
        
        Query query1 = em.createQuery("SELECT e FROM Employee e WHERE e.role = ?1");
        query1.setParameter(1, RoleEnum.DOCTOR);
        List<Employee> doctors = query1.getResultList();
        int doctorsSize = doctors.size();
        
        Query query2 = em.createQuery("SELECT m FROM MedicalRecord m");
        List<MedicalRecord> medical_records = query2.getResultList();
        int mrsSize = medical_records.size();
        
        int[][] times = {{8,0},{8,15},{8,30},{8,45},{9,0},{9,15},{9,30},{9,45},{10,0},{10,15},{10,30},{10,45},{11,0},{11,15},{11,30},{11,45},
            {14,0},{14,15},{14,30},{14,45},{15,0},{15,15},{15,30},{15,45},{16,0},{16,15},{16,30},{16,45},{17,0},{17,15},{17,30},{17,45}};
        Random random = new Random();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, monthsBefore);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);

        Calendar upperBound = Calendar.getInstance();
        upperBound.set(upperBound.get(Calendar.YEAR), upperBound.get(Calendar.MONTH), upperBound.get(Calendar.DATE), 0, 0, 0);
        
        // --- Start of randomizing past appointments ---
        while (c.getTime().getTime() < upperBound.getTime().getTime()) {
            
            if (c.get(Calendar.DAY_OF_WEEK) != 1 && c.get(Calendar.DAY_OF_WEEK) != 7) {
                
                for (int i = 0; i < times.length; i++) {
                    c.set(Calendar.HOUR_OF_DAY, times[i][0]);
                    c.set(Calendar.MINUTE, times[i][1]);

                    if(random.nextDouble() < p) {
                        
                        double p_type = random.nextDouble();
                        AppointmentTypeEnum type;
                        if (p_type < p_vaccination) type = AppointmentTypeEnum.VACCINATION;
                        else if (p_type < p_vaccination + p_checkup) type = AppointmentTypeEnum.HEALTH_CHECKUP;
                        else type = AppointmentTypeEnum.CONSULTATION;
                        
                        double p_status = random.nextDouble();
                        StatusEnum status;
                        if (p_status < p_missed) status = StatusEnum.MISSED;
                        else if (p_status < p_missed + p_cancelled) status = StatusEnum.CANCELLED;
                        else status = StatusEnum.COMPLETED;
                        
                        double p_appointmentType = random.nextDouble();
                        ScheduleTypeEnum appointmentType;
                        if (p_appointmentType < p_walkin) {
                            appointmentType = ScheduleTypeEnum.WALK_IN;
                            Appointment a = new Appointment(null, medical_records.get((i%mrsSize)), c.getTime(), appointmentType, type, status);
                            em.persist(a);em.flush();
                            a.setQueue_no(String.format("W%03d", a.getId()));
                        } else {
                            appointmentType = ScheduleTypeEnum.APPOINTMENT;
                            Appointment a = new Appointment(doctors.get(i%doctorsSize), medical_records.get((i%mrsSize)), c.getTime(), appointmentType, type, status);
                            em.persist(a);em.flush();
                            a.setQueue_no(String.format("A%03d", a.getId()));
                        }
                        
                    }
                }
                
            }

            c.add(Calendar.DATE, 1);
        }
        // --- End of randomizing past appointments ---
        
        upperBound.add(Calendar.MONTH, monthsAfter);
        
        // --- Start of randomizing future appointments ---
        while (c.getTime().getTime() < upperBound.getTime().getTime()) {
            
            if (c.get(Calendar.DAY_OF_WEEK) != 1 && c.get(Calendar.DAY_OF_WEEK) != 7) {
            
                for (int i = 0; i < times.length; i++) {
                    c.set(Calendar.HOUR_OF_DAY, times[i][0]);
                    c.set(Calendar.MINUTE, times[i][1]);

                    if(random.nextDouble() < p) {
                        
                        double p_type = random.nextDouble();
                        AppointmentTypeEnum type;
                        if (p_type < p_vaccination) type = AppointmentTypeEnum.VACCINATION;
                        else if (p_type < p_vaccination + p_checkup) type = AppointmentTypeEnum.HEALTH_CHECKUP;
                        else type = AppointmentTypeEnum.CONSULTATION;
                        
                        Appointment a = new Appointment(doctors.get(i%doctorsSize), medical_records.get((i%mrsSize)), c.getTime(), ScheduleTypeEnum.APPOINTMENT, type, StatusEnum.BOOKED);
                        em.persist(a);em.flush();
                        a.setQueue_no(String.format("A%03d", a.getId()));
                        
                    }
                }
                
            }
            
            c.add(Calendar.DATE, 1);
        }
        // --- End of randomizing future appointments ---
    }

}
