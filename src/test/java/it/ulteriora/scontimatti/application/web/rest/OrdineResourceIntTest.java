package it.ulteriora.scontimatti.application.web.rest;

import it.ulteriora.scontimatti.application.ScontimattiApp;

import it.ulteriora.scontimatti.application.domain.Ordine;
import it.ulteriora.scontimatti.application.repository.OrdineRepository;
import it.ulteriora.scontimatti.application.service.OrdineService;
import it.ulteriora.scontimatti.application.service.dto.OrdineDTO;
import it.ulteriora.scontimatti.application.service.mapper.OrdineMapper;
import it.ulteriora.scontimatti.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static it.ulteriora.scontimatti.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import it.ulteriora.scontimatti.application.domain.enumeration.StatoOrdine;
/**
 * Test class for the OrdineResource REST controller.
 *
 * @see OrdineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScontimattiApp.class)
public class OrdineResourceIntTest {

    private static final Long DEFAULT_PROGRESSIVO = 1L;
    private static final Long UPDATED_PROGRESSIVO = 2L;

    private static final Long DEFAULT_NUMERO_ORDINE = 1L;
    private static final Long UPDATED_NUMERO_ORDINE = 2L;

    private static final LocalDate DEFAULT_DATA_ORDINE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ORDINE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PAGAMENTO_EFFETTIVO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAGAMENTO_EFFETTIVO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTALE_COMPENSARE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTALE_COMPENSARE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTALE_COMPENSATO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTALE_COMPENSATO = new BigDecimal(2);

    private static final StatoOrdine DEFAULT_STATO_ORDINE = StatoOrdine.ATTESA_PAGAMENTO;
    private static final StatoOrdine UPDATED_STATO_ORDINE = StatoOrdine.PAGATO;

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private OrdineMapper ordineMapper;

    @Autowired
    private OrdineService ordineService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrdineMockMvc;

    private Ordine ordine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrdineResource ordineResource = new OrdineResource(ordineService);
        this.restOrdineMockMvc = MockMvcBuilders.standaloneSetup(ordineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ordine createEntity(EntityManager em) {
        Ordine ordine = new Ordine()
            .progressivo(DEFAULT_PROGRESSIVO)
            .numeroOrdine(DEFAULT_NUMERO_ORDINE)
            .dataOrdine(DEFAULT_DATA_ORDINE)
            .pagamentoEffettivo(DEFAULT_PAGAMENTO_EFFETTIVO)
            .totaleCompensare(DEFAULT_TOTALE_COMPENSARE)
            .totaleCompensato(DEFAULT_TOTALE_COMPENSATO)
            .statoOrdine(DEFAULT_STATO_ORDINE);
        return ordine;
    }

    @Before
    public void initTest() {
        ordine = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdine() throws Exception {
        int databaseSizeBeforeCreate = ordineRepository.findAll().size();

        // Create the Ordine
        OrdineDTO ordineDTO = ordineMapper.toDto(ordine);
        restOrdineMockMvc.perform(post("/api/ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordineDTO)))
            .andExpect(status().isCreated());

        // Validate the Ordine in the database
        List<Ordine> ordineList = ordineRepository.findAll();
        assertThat(ordineList).hasSize(databaseSizeBeforeCreate + 1);
        Ordine testOrdine = ordineList.get(ordineList.size() - 1);
        assertThat(testOrdine.getProgressivo()).isEqualTo(DEFAULT_PROGRESSIVO);
        assertThat(testOrdine.getNumeroOrdine()).isEqualTo(DEFAULT_NUMERO_ORDINE);
        assertThat(testOrdine.getDataOrdine()).isEqualTo(DEFAULT_DATA_ORDINE);
        assertThat(testOrdine.getPagamentoEffettivo()).isEqualTo(DEFAULT_PAGAMENTO_EFFETTIVO);
        assertThat(testOrdine.getTotaleCompensare()).isEqualTo(DEFAULT_TOTALE_COMPENSARE);
        assertThat(testOrdine.getTotaleCompensato()).isEqualTo(DEFAULT_TOTALE_COMPENSATO);
        assertThat(testOrdine.getStatoOrdine()).isEqualTo(DEFAULT_STATO_ORDINE);
    }

    @Test
    @Transactional
    public void createOrdineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordineRepository.findAll().size();

        // Create the Ordine with an existing ID
        ordine.setId(1L);
        OrdineDTO ordineDTO = ordineMapper.toDto(ordine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdineMockMvc.perform(post("/api/ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ordine in the database
        List<Ordine> ordineList = ordineRepository.findAll();
        assertThat(ordineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProgressivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordineRepository.findAll().size();
        // set the field null
        ordine.setProgressivo(null);

        // Create the Ordine, which fails.
        OrdineDTO ordineDTO = ordineMapper.toDto(ordine);

        restOrdineMockMvc.perform(post("/api/ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordineDTO)))
            .andExpect(status().isBadRequest());

        List<Ordine> ordineList = ordineRepository.findAll();
        assertThat(ordineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroOrdineIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordineRepository.findAll().size();
        // set the field null
        ordine.setNumeroOrdine(null);

        // Create the Ordine, which fails.
        OrdineDTO ordineDTO = ordineMapper.toDto(ordine);

        restOrdineMockMvc.perform(post("/api/ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordineDTO)))
            .andExpect(status().isBadRequest());

        List<Ordine> ordineList = ordineRepository.findAll();
        assertThat(ordineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataOrdineIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordineRepository.findAll().size();
        // set the field null
        ordine.setDataOrdine(null);

        // Create the Ordine, which fails.
        OrdineDTO ordineDTO = ordineMapper.toDto(ordine);

        restOrdineMockMvc.perform(post("/api/ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordineDTO)))
            .andExpect(status().isBadRequest());

        List<Ordine> ordineList = ordineRepository.findAll();
        assertThat(ordineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPagamentoEffettivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordineRepository.findAll().size();
        // set the field null
        ordine.setPagamentoEffettivo(null);

        // Create the Ordine, which fails.
        OrdineDTO ordineDTO = ordineMapper.toDto(ordine);

        restOrdineMockMvc.perform(post("/api/ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordineDTO)))
            .andExpect(status().isBadRequest());

        List<Ordine> ordineList = ordineRepository.findAll();
        assertThat(ordineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatoOrdineIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordineRepository.findAll().size();
        // set the field null
        ordine.setStatoOrdine(null);

        // Create the Ordine, which fails.
        OrdineDTO ordineDTO = ordineMapper.toDto(ordine);

        restOrdineMockMvc.perform(post("/api/ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordineDTO)))
            .andExpect(status().isBadRequest());

        List<Ordine> ordineList = ordineRepository.findAll();
        assertThat(ordineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrdines() throws Exception {
        // Initialize the database
        ordineRepository.saveAndFlush(ordine);

        // Get all the ordineList
        restOrdineMockMvc.perform(get("/api/ordines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordine.getId().intValue())))
            .andExpect(jsonPath("$.[*].progressivo").value(hasItem(DEFAULT_PROGRESSIVO.intValue())))
            .andExpect(jsonPath("$.[*].numeroOrdine").value(hasItem(DEFAULT_NUMERO_ORDINE.intValue())))
            .andExpect(jsonPath("$.[*].dataOrdine").value(hasItem(DEFAULT_DATA_ORDINE.toString())))
            .andExpect(jsonPath("$.[*].pagamentoEffettivo").value(hasItem(DEFAULT_PAGAMENTO_EFFETTIVO.intValue())))
            .andExpect(jsonPath("$.[*].totaleCompensare").value(hasItem(DEFAULT_TOTALE_COMPENSARE.intValue())))
            .andExpect(jsonPath("$.[*].totaleCompensato").value(hasItem(DEFAULT_TOTALE_COMPENSATO.intValue())))
            .andExpect(jsonPath("$.[*].statoOrdine").value(hasItem(DEFAULT_STATO_ORDINE.toString())));
    }

    @Test
    @Transactional
    public void getOrdine() throws Exception {
        // Initialize the database
        ordineRepository.saveAndFlush(ordine);

        // Get the ordine
        restOrdineMockMvc.perform(get("/api/ordines/{id}", ordine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordine.getId().intValue()))
            .andExpect(jsonPath("$.progressivo").value(DEFAULT_PROGRESSIVO.intValue()))
            .andExpect(jsonPath("$.numeroOrdine").value(DEFAULT_NUMERO_ORDINE.intValue()))
            .andExpect(jsonPath("$.dataOrdine").value(DEFAULT_DATA_ORDINE.toString()))
            .andExpect(jsonPath("$.pagamentoEffettivo").value(DEFAULT_PAGAMENTO_EFFETTIVO.intValue()))
            .andExpect(jsonPath("$.totaleCompensare").value(DEFAULT_TOTALE_COMPENSARE.intValue()))
            .andExpect(jsonPath("$.totaleCompensato").value(DEFAULT_TOTALE_COMPENSATO.intValue()))
            .andExpect(jsonPath("$.statoOrdine").value(DEFAULT_STATO_ORDINE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdine() throws Exception {
        // Get the ordine
        restOrdineMockMvc.perform(get("/api/ordines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdine() throws Exception {
        // Initialize the database
        ordineRepository.saveAndFlush(ordine);
        int databaseSizeBeforeUpdate = ordineRepository.findAll().size();

        // Update the ordine
        Ordine updatedOrdine = ordineRepository.findOne(ordine.getId());
        // Disconnect from session so that the updates on updatedOrdine are not directly saved in db
        em.detach(updatedOrdine);
        updatedOrdine
            .progressivo(UPDATED_PROGRESSIVO)
            .numeroOrdine(UPDATED_NUMERO_ORDINE)
            .dataOrdine(UPDATED_DATA_ORDINE)
            .pagamentoEffettivo(UPDATED_PAGAMENTO_EFFETTIVO)
            .totaleCompensare(UPDATED_TOTALE_COMPENSARE)
            .totaleCompensato(UPDATED_TOTALE_COMPENSATO)
            .statoOrdine(UPDATED_STATO_ORDINE);
        OrdineDTO ordineDTO = ordineMapper.toDto(updatedOrdine);

        restOrdineMockMvc.perform(put("/api/ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordineDTO)))
            .andExpect(status().isOk());

        // Validate the Ordine in the database
        List<Ordine> ordineList = ordineRepository.findAll();
        assertThat(ordineList).hasSize(databaseSizeBeforeUpdate);
        Ordine testOrdine = ordineList.get(ordineList.size() - 1);
        assertThat(testOrdine.getProgressivo()).isEqualTo(UPDATED_PROGRESSIVO);
        assertThat(testOrdine.getNumeroOrdine()).isEqualTo(UPDATED_NUMERO_ORDINE);
        assertThat(testOrdine.getDataOrdine()).isEqualTo(UPDATED_DATA_ORDINE);
        assertThat(testOrdine.getPagamentoEffettivo()).isEqualTo(UPDATED_PAGAMENTO_EFFETTIVO);
        assertThat(testOrdine.getTotaleCompensare()).isEqualTo(UPDATED_TOTALE_COMPENSARE);
        assertThat(testOrdine.getTotaleCompensato()).isEqualTo(UPDATED_TOTALE_COMPENSATO);
        assertThat(testOrdine.getStatoOrdine()).isEqualTo(UPDATED_STATO_ORDINE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdine() throws Exception {
        int databaseSizeBeforeUpdate = ordineRepository.findAll().size();

        // Create the Ordine
        OrdineDTO ordineDTO = ordineMapper.toDto(ordine);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrdineMockMvc.perform(put("/api/ordines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordineDTO)))
            .andExpect(status().isCreated());

        // Validate the Ordine in the database
        List<Ordine> ordineList = ordineRepository.findAll();
        assertThat(ordineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrdine() throws Exception {
        // Initialize the database
        ordineRepository.saveAndFlush(ordine);
        int databaseSizeBeforeDelete = ordineRepository.findAll().size();

        // Get the ordine
        restOrdineMockMvc.perform(delete("/api/ordines/{id}", ordine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ordine> ordineList = ordineRepository.findAll();
        assertThat(ordineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ordine.class);
        Ordine ordine1 = new Ordine();
        ordine1.setId(1L);
        Ordine ordine2 = new Ordine();
        ordine2.setId(ordine1.getId());
        assertThat(ordine1).isEqualTo(ordine2);
        ordine2.setId(2L);
        assertThat(ordine1).isNotEqualTo(ordine2);
        ordine1.setId(null);
        assertThat(ordine1).isNotEqualTo(ordine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrdineDTO.class);
        OrdineDTO ordineDTO1 = new OrdineDTO();
        ordineDTO1.setId(1L);
        OrdineDTO ordineDTO2 = new OrdineDTO();
        assertThat(ordineDTO1).isNotEqualTo(ordineDTO2);
        ordineDTO2.setId(ordineDTO1.getId());
        assertThat(ordineDTO1).isEqualTo(ordineDTO2);
        ordineDTO2.setId(2L);
        assertThat(ordineDTO1).isNotEqualTo(ordineDTO2);
        ordineDTO1.setId(null);
        assertThat(ordineDTO1).isNotEqualTo(ordineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(ordineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(ordineMapper.fromId(null)).isNull();
    }
}
