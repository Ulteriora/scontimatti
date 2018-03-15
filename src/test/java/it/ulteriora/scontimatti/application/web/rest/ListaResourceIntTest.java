package it.ulteriora.scontimatti.application.web.rest;

import it.ulteriora.scontimatti.application.ScontimattiApp;

import it.ulteriora.scontimatti.application.domain.Lista;
import it.ulteriora.scontimatti.application.repository.ListaRepository;
import it.ulteriora.scontimatti.application.service.ListaService;
import it.ulteriora.scontimatti.application.service.dto.ListaDTO;
import it.ulteriora.scontimatti.application.service.mapper.ListaMapper;
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
 * Test class for the ListaResource REST controller.
 *
 * @see ListaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScontimattiApp.class)
public class ListaResourceIntTest {

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

    private static final BigDecimal DEFAULT_TOTALE_COMPENTATO = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTALE_COMPENTATO = new BigDecimal(2);

    private static final StatoOrdine DEFAULT_STATO_ORDINE = StatoOrdine.ATTESA_PAGAMENTO;
    private static final StatoOrdine UPDATED_STATO_ORDINE = StatoOrdine.PAGATO;

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private ListaMapper listaMapper;

    @Autowired
    private ListaService listaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restListaMockMvc;

    private Lista lista;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListaResource listaResource = new ListaResource(listaService);
        this.restListaMockMvc = MockMvcBuilders.standaloneSetup(listaResource)
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
    public static Lista createEntity(EntityManager em) {
        Lista lista = new Lista()
            .progressivo(DEFAULT_PROGRESSIVO)
            .numeroOrdine(DEFAULT_NUMERO_ORDINE)
            .dataOrdine(DEFAULT_DATA_ORDINE)
            .pagamentoEffettivo(DEFAULT_PAGAMENTO_EFFETTIVO)
            .totaleCompensare(DEFAULT_TOTALE_COMPENSARE)
            .totaleCompentato(DEFAULT_TOTALE_COMPENTATO)
            .statoOrdine(DEFAULT_STATO_ORDINE);
        return lista;
    }

    @Before
    public void initTest() {
        lista = createEntity(em);
    }

    @Test
    @Transactional
    public void createLista() throws Exception {
        int databaseSizeBeforeCreate = listaRepository.findAll().size();

        // Create the Lista
        ListaDTO listaDTO = listaMapper.toDto(lista);
        restListaMockMvc.perform(post("/api/listas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listaDTO)))
            .andExpect(status().isCreated());

        // Validate the Lista in the database
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeCreate + 1);
        Lista testLista = listaList.get(listaList.size() - 1);
        assertThat(testLista.getProgressivo()).isEqualTo(DEFAULT_PROGRESSIVO);
        assertThat(testLista.getNumeroOrdine()).isEqualTo(DEFAULT_NUMERO_ORDINE);
        assertThat(testLista.getDataOrdine()).isEqualTo(DEFAULT_DATA_ORDINE);
        assertThat(testLista.getPagamentoEffettivo()).isEqualTo(DEFAULT_PAGAMENTO_EFFETTIVO);
        assertThat(testLista.getTotaleCompensare()).isEqualTo(DEFAULT_TOTALE_COMPENSARE);
        assertThat(testLista.getTotaleCompentato()).isEqualTo(DEFAULT_TOTALE_COMPENTATO);
        assertThat(testLista.getStatoOrdine()).isEqualTo(DEFAULT_STATO_ORDINE);
    }

    @Test
    @Transactional
    public void createListaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listaRepository.findAll().size();

        // Create the Lista with an existing ID
        lista.setId(1L);
        ListaDTO listaDTO = listaMapper.toDto(lista);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListaMockMvc.perform(post("/api/listas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lista in the database
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProgressivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = listaRepository.findAll().size();
        // set the field null
        lista.setProgressivo(null);

        // Create the Lista, which fails.
        ListaDTO listaDTO = listaMapper.toDto(lista);

        restListaMockMvc.perform(post("/api/listas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listaDTO)))
            .andExpect(status().isBadRequest());

        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListas() throws Exception {
        // Initialize the database
        listaRepository.saveAndFlush(lista);

        // Get all the listaList
        restListaMockMvc.perform(get("/api/listas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lista.getId().intValue())))
            .andExpect(jsonPath("$.[*].progressivo").value(hasItem(DEFAULT_PROGRESSIVO.intValue())))
            .andExpect(jsonPath("$.[*].numeroOrdine").value(hasItem(DEFAULT_NUMERO_ORDINE.intValue())))
            .andExpect(jsonPath("$.[*].dataOrdine").value(hasItem(DEFAULT_DATA_ORDINE.toString())))
            .andExpect(jsonPath("$.[*].pagamentoEffettivo").value(hasItem(DEFAULT_PAGAMENTO_EFFETTIVO.intValue())))
            .andExpect(jsonPath("$.[*].totaleCompensare").value(hasItem(DEFAULT_TOTALE_COMPENSARE.intValue())))
            .andExpect(jsonPath("$.[*].totaleCompentato").value(hasItem(DEFAULT_TOTALE_COMPENTATO.intValue())))
            .andExpect(jsonPath("$.[*].statoOrdine").value(hasItem(DEFAULT_STATO_ORDINE.toString())));
    }

    @Test
    @Transactional
    public void getLista() throws Exception {
        // Initialize the database
        listaRepository.saveAndFlush(lista);

        // Get the lista
        restListaMockMvc.perform(get("/api/listas/{id}", lista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lista.getId().intValue()))
            .andExpect(jsonPath("$.progressivo").value(DEFAULT_PROGRESSIVO.intValue()))
            .andExpect(jsonPath("$.numeroOrdine").value(DEFAULT_NUMERO_ORDINE.intValue()))
            .andExpect(jsonPath("$.dataOrdine").value(DEFAULT_DATA_ORDINE.toString()))
            .andExpect(jsonPath("$.pagamentoEffettivo").value(DEFAULT_PAGAMENTO_EFFETTIVO.intValue()))
            .andExpect(jsonPath("$.totaleCompensare").value(DEFAULT_TOTALE_COMPENSARE.intValue()))
            .andExpect(jsonPath("$.totaleCompentato").value(DEFAULT_TOTALE_COMPENTATO.intValue()))
            .andExpect(jsonPath("$.statoOrdine").value(DEFAULT_STATO_ORDINE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLista() throws Exception {
        // Get the lista
        restListaMockMvc.perform(get("/api/listas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLista() throws Exception {
        // Initialize the database
        listaRepository.saveAndFlush(lista);
        int databaseSizeBeforeUpdate = listaRepository.findAll().size();

        // Update the lista
        Lista updatedLista = listaRepository.findOne(lista.getId());
        // Disconnect from session so that the updates on updatedLista are not directly saved in db
        em.detach(updatedLista);
        updatedLista
            .progressivo(UPDATED_PROGRESSIVO)
            .numeroOrdine(UPDATED_NUMERO_ORDINE)
            .dataOrdine(UPDATED_DATA_ORDINE)
            .pagamentoEffettivo(UPDATED_PAGAMENTO_EFFETTIVO)
            .totaleCompensare(UPDATED_TOTALE_COMPENSARE)
            .totaleCompentato(UPDATED_TOTALE_COMPENTATO)
            .statoOrdine(UPDATED_STATO_ORDINE);
        ListaDTO listaDTO = listaMapper.toDto(updatedLista);

        restListaMockMvc.perform(put("/api/listas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listaDTO)))
            .andExpect(status().isOk());

        // Validate the Lista in the database
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeUpdate);
        Lista testLista = listaList.get(listaList.size() - 1);
        assertThat(testLista.getProgressivo()).isEqualTo(UPDATED_PROGRESSIVO);
        assertThat(testLista.getNumeroOrdine()).isEqualTo(UPDATED_NUMERO_ORDINE);
        assertThat(testLista.getDataOrdine()).isEqualTo(UPDATED_DATA_ORDINE);
        assertThat(testLista.getPagamentoEffettivo()).isEqualTo(UPDATED_PAGAMENTO_EFFETTIVO);
        assertThat(testLista.getTotaleCompensare()).isEqualTo(UPDATED_TOTALE_COMPENSARE);
        assertThat(testLista.getTotaleCompentato()).isEqualTo(UPDATED_TOTALE_COMPENTATO);
        assertThat(testLista.getStatoOrdine()).isEqualTo(UPDATED_STATO_ORDINE);
    }

    @Test
    @Transactional
    public void updateNonExistingLista() throws Exception {
        int databaseSizeBeforeUpdate = listaRepository.findAll().size();

        // Create the Lista
        ListaDTO listaDTO = listaMapper.toDto(lista);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restListaMockMvc.perform(put("/api/listas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listaDTO)))
            .andExpect(status().isCreated());

        // Validate the Lista in the database
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLista() throws Exception {
        // Initialize the database
        listaRepository.saveAndFlush(lista);
        int databaseSizeBeforeDelete = listaRepository.findAll().size();

        // Get the lista
        restListaMockMvc.perform(delete("/api/listas/{id}", lista.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lista.class);
        Lista lista1 = new Lista();
        lista1.setId(1L);
        Lista lista2 = new Lista();
        lista2.setId(lista1.getId());
        assertThat(lista1).isEqualTo(lista2);
        lista2.setId(2L);
        assertThat(lista1).isNotEqualTo(lista2);
        lista1.setId(null);
        assertThat(lista1).isNotEqualTo(lista2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListaDTO.class);
        ListaDTO listaDTO1 = new ListaDTO();
        listaDTO1.setId(1L);
        ListaDTO listaDTO2 = new ListaDTO();
        assertThat(listaDTO1).isNotEqualTo(listaDTO2);
        listaDTO2.setId(listaDTO1.getId());
        assertThat(listaDTO1).isEqualTo(listaDTO2);
        listaDTO2.setId(2L);
        assertThat(listaDTO1).isNotEqualTo(listaDTO2);
        listaDTO1.setId(null);
        assertThat(listaDTO1).isNotEqualTo(listaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(listaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(listaMapper.fromId(null)).isNull();
    }
}
