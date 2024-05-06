package pa.com.sura.catalogs.controllers;

import pa.com.sura.catalogs.models.dto.mappings.ListOfMapsDTO;
import pa.com.sura.catalogs.models.dto.mappings.MapSystemDTO;
import pa.com.sura.catalogs.models.entities.mobilityplatform.CatalogType;
import pa.com.sura.catalogs.models.entities.premium.CatalogItem;
import pa.com.sura.catalogs.models.entities.mobilityplatform.MapTable;
import pa.com.sura.catalogs.models.dto.mappings.MappingObjectDTO;
import pa.com.sura.catalogs.models.errors.ApiError;
import pa.com.sura.catalogs.services.ApiCatalogsService;
import pa.com.sura.catalogs.services.CatalogTypeService;
import pa.com.sura.catalogs.services.ProcedurePremService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Stream;

@RestController
public class ApiCatalogsController {
    private static final Logger log = LoggerFactory.getLogger(ApiCatalogsController.class);
    private static final String MAPPING_TYPE_NAME = "mappingType";
    private static final String SYSTEM_A_NAME = "systemA";
    private static final String SYSTEM_B_NAME = "systemB";
    private static final String CODE_NAME = "code";
    private static final String MODE_NAME = "mode";
    private static final String ATOB_MODE = "atob";
    private static final String BTOA_MODE = "btoa";
    private static final String ALL_MODE = "allcoincidences";

    @Autowired
    private ApiCatalogsService service;

    @Autowired
    private CatalogTypeService catalogTypeService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Environment env;

    @Autowired
    private ProcedurePremService procedurePremService;
    @Autowired
    private HttpServletRequest httpServletRequest;
    /*
    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
    */

    /**
     * REST Controller para <b><code>@GetMapping("/api/mappings")</code></b> que retorna una lista de objetos 
     * <b><code>MappingObjectDTO</code></b> a partir de una serie de parámetros que permiten buscar un mapeo específico
     * entre el código manejado por un <b>Sistema A</b> y un <b>Sistema B</b>, para un catálogo específico.
     * Los mapeos de códigos están definidos en la tabla de base de datos <b><code>map_table</code></b> la cual está 
     * vinculada a la entidad JPA <b><code>MapTable</code></b>.
     * @param   mappingType Query param (request param) que indica el catálogo en donde se realizará el mapeo.
     * @param   systemA     Query param (request param) que indica al <b>Sistema A</b>, el cual puede ser tratado
     * como un <i>sistema de orígen</i> o un <i>sistema de destino</i>, dependiendo del parámetro <code>mode</code>
     * (<b><code>atob</code></b> o <b><code>btoa</code></b> respectivamente).
     * @param   systemB     Query param (request param) que indica al <b>Sistema B</b>, el cual puede ser tratado
     * como un <i>sistema de orígen</i> o un <i>sistema de destino</i>, dependiendo del parámetro <code>mode</code>
     * (<b><code>btoa</code></b> o <b><code>atob</code></b> respectivamente).
     * @param   code        Query param (request param) que indica el código que se quiere mapear dentro del catálogo
     * indicado en el parámetro <b><code>mappingType</code></b>.
     * El parámetro <b><code>code</code></b> se asociará al sistema de origen según lo que se indique en el parámetro
     * <b><code>mode</code></b>: "atob" (sistema de origen es A) o "btoa" (sistema de origen es B).
     * @param   mode        Indica la "dirección" del mapeo:<br>
     *                      - <b><code>atob</code></b>: Desde un <b>sistema A</b> a un <b>sistema B</b>.<br>
     *                      - <b><code>btoa</code></b>: Desde un <b>sistema B</b> a un <b>sistema A</b>.<br>
     *                      - <b><code>allcoincidences</code></b>: Todos los mapeos que se encuentren ya sea desde un
     *                      <b>sistema A</b> a un <b>sistema B</b> o desde un <b>sistema B</b> a un <b>sistema A</b>.<br>
     * @return Una atributo <b><code>content</code></b> que contiene una lista o arreglo de objetos 
     * <b><code>MappingObjectDTO</code></b> que contienen a todos los mapeos encontrados según los parámetros 
     * especificados en los query params (request params).
     * @see MappingObjectDTO
     * @see MapTable
     */

    @GetMapping("/api/catalogs")
    public ResponseEntity<?> getCatalogs(
            @RequestHeader(name="x-organization-id") String xOrganizationId,
            @RequestHeader(name="x-organization-type") String xOrganizationType,
            @RequestHeader(name="x-client-application") String xClientApplication,
            @RequestHeader(name="x-trace-id") String xTraceId,
            @RequestHeader(name="x-user") String xUser,
            @RequestParam(name="catalogType") @NotNull String catalogType,
            @RequestParam(value = "filters", required = false) @Valid String filters
    ) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> traducciones = new HashMap<>();

        traducciones.put("COUNTRIES", "PAISES");
        traducciones.put("STATES", "PROVINCIAS");
        traducciones.put("REGIONS", "DISTRITOS");
        traducciones.put("CITIES", "CORREGIMIENTOS");
        traducciones.put("OCCUPATIONS", "OCUPACIONES");
        traducciones.put("MAKERS", "MARCAS");
        traducciones.put("MODELS", "MODELOS");
        traducciones.put("COLORS", "COLORES");
        traducciones.put("CIRCULATION_AREAS", "ZONAS_DE_CIRCULACION");
        traducciones.put("CHASSIS_TYPES", "TIPOS_DE_CHASIS");
        traducciones.put("UNIT_USES", "USOS_DE_UNIDAD");
        traducciones.put("CREDITORS", "ACREEDORES");
        traducciones.put("RAMOS", "RAMOS");
        traducciones.put("SEXS", "SEXOS");
        traducciones.put("MATRIAL_STATUSES", "ECIVIL");

        catalogType = traducciones.getOrDefault(catalogType, catalogType);

        if (filters != null){
            String[] pairs = filters.split(",");

            StringBuilder concatenatedValues = new StringBuilder();

            for (String pair : pairs) {

                String[] keyValue = pair.split("=");
                concatenatedValues.append(keyValue[1]);
            }

            filters = concatenatedValues.toString();
        }

        try {
            List<CatalogItem> catalogItems = procedurePremService.findCatalogs(catalogType,filters);

            response.put("content", catalogItems);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (DataAccessException e) {
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/api/catalogs-types")
    public ResponseEntity<?> getCatalogTypes(
            @RequestHeader("x-organization-id") String xOrganizationId,
            @RequestHeader("x-organization-type") String xOrganizationType,
            @RequestHeader("x-client-application") String xClientApplication,
            @RequestHeader("x-trace-id") String xTraceId,
            @RequestHeader("x-user") String xUser,
            @RequestHeader(value = "x-client-application-flow", required = false) String xClientApplicationFlow
    ) {
        Map<String, Object> response = new HashMap<String, Object>();

        List<CatalogType> types = catalogTypeService.findAll();
        response.put("content", types);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @GetMapping("/api/mappings")
    public ResponseEntity<?> findMappings(
            @RequestHeader Map<String, String> headers,
            @RequestHeader(name = "x-organization-id") String xOrganizationId,
            @RequestHeader(name = "x-organization-type") String xOrganizationType,
            @RequestHeader(name = "x-client-application") String xClientApplication,
            @RequestHeader(name = "x-client-application-flow", required = false) String xClientApplicationFlow,
            @RequestHeader(name = "x-trace-id") String xTraceId,
            @RequestHeader(name = "x-user") String xUser,
            @RequestParam(name = MAPPING_TYPE_NAME) String mappingType,
            @RequestParam(name = SYSTEM_A_NAME) String systemA,
            @RequestParam(name = SYSTEM_B_NAME) String systemB,
            @RequestParam(name = CODE_NAME, required = false) String code,
            @RequestParam(name = MODE_NAME) String mode
    ) {
        logMessage(
                "findMap", headers, xOrganizationId, xOrganizationType, xClientApplication, xClientApplicationFlow, xTraceId,
                xUser, mappingType, systemA, systemB, code, mode
        );
        Map<String, List<MappingObjectDTO>> body = new LinkedHashMap<>(); //Se utiliza LinkedHashMap para que los key del map aparezcan ordenados en la salida del JSON.
        Optional <List<MapTable>> opt = Optional.empty();
        if (ATOB_MODE.equalsIgnoreCase(mode)) //Si el parámetro "mode" tiene el valor "atob", se busca un mapeo en la dirección A -> B.
            opt = service.findByAtoB(mappingType, systemA, systemB, code);
        else if (BTOA_MODE.equalsIgnoreCase(mode)) //Si el parámetro "mode" tiene el valor "btoa", se busca un mapeo en la dirección B -> A.
            opt = service.findByBtoA(mappingType, systemB, systemA, code);
        else if (ALL_MODE.equalsIgnoreCase(mode)) //Si el parámetro "mode" tiene el valor "allcoincidences", se buscan todos los mapeos que coninciden en ambas direcciones.
            opt = service.findAllMapCoincidences(mappingType, systemA, systemB, code);

        if (opt.isPresent() &&  !opt.get().isEmpty()) {
            List<MapTable> mapTables = opt.get();
            List<MappingObjectDTO> mappinObjects = getMappingObjects(mapTables.stream());
            body.put("content", mappinObjects);
            return ResponseEntity.ok(body);
        }
        /*
         Si la búsqueda de mapeos no encuentra nada, se devuelve un mensaje de error que indica que el contenido
         no fue encontrado.
        */
        ApiError apiError = createResourceNotFoundError(xTraceId, "", MAPPING_TYPE_NAME, SYSTEM_A_NAME, SYSTEM_B_NAME, MODE_NAME, CODE_NAME);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    /**
     * REST Controller para <b><code>@PostMapping("/api/mappings/pages/{pageNumber}")</code></b> que retorna una lista
     * de objetos <b><code>MappingObjectDTO</code></b> con todos los mapeos entre un <b>sitema A</b> y un
     * <b>sistema B</b>.
     * Los mapeos de códigos están definidos en la tabla de base de datos <b><code>map_table</code></b> la cual está
     * vinculada a la entidad JPA <b><code>MapTable</code></b>.
     * @param   mappingType Query param (request param) que indica el catálogo en donde se realizará el mapeo.
     * @param   systemA     Query param (request param) que indica al <b>Sistema A</b>, el cual puede ser tratado
     * como un <i>sistema de orígen</i> o un <i>sistema de destino</i>, dependiendo del parámetro <code>mode</code>
     * (<b><code>atob</code></b> o <b><code>btoa</code></b> respectivamente).
     * @param   systemB     Query param (request param) que indica al <b>Sistema B</b>, el cual puede ser tratado
     * como un <i>sistema de orígen</i> o un <i>sistema de destino</i>, dependiendo del parámetro <code>mode</code>
     * (<b><code>btoa</code></b> o <b><code>atob</code></b> respectivamente).
     * @param   mode        Indica la "dirección" del mapeo:<br>
     *                      - <b><code>atob</code></b>: Desde un <b>sistema A</b> a un <b>sistema B</b>.<br>
     *                      - <b><code>btoa</code></b>: Desde un <b>sistema B</b> a un <b>sistema A</b>.<br>
     * @return Una atributo <b><code>content</code></b> que contiene una lista o arreglo de objetos
     * <b><code>MappingObjectDTO</code></b> que contienen a todos los mapeos encontrados según los parámetros
     * especificados en los query params (request params).
     * @see MappingObjectDTO
     * @see MapTable
     */
    @PostMapping("/api/mappings/pages/{pageNumber}")
    public ResponseEntity<?> findAllBySistems(
            @RequestHeader(name = "x-organization-id") String xOrganizationId,
            @RequestHeader(name = "x-organization-type") String xOrganizationType,
            @RequestHeader(name = "x-client-application") String xClientApplication,
            @RequestHeader(name = "x-client-application-flow", required = false) String xClientApplicationFlow,
            @RequestHeader(name = "x-trace-id") String xTraceId,
            @RequestHeader(name = "x-user") String xUser,
            @RequestParam(name = MAPPING_TYPE_NAME) String mappingType,
            @RequestParam(name = SYSTEM_A_NAME) String systemA,
            @RequestParam(name = SYSTEM_B_NAME) String systemB,
            @RequestParam(name = MODE_NAME) String mode,
            @PathVariable Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestBody ListOfMapsDTO codes
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Page.empty().getSort().ascending());
        List<String> objectList = codes.getObjects().stream().map(o -> o.get("code")).toList();
        Page<MapTable> page = null;

        if (ATOB_MODE.equalsIgnoreCase(mode))
            page = service.findByAtoB(mappingType, systemA, systemB, objectList, pageable);
        else if (BTOA_MODE.equalsIgnoreCase(mode))
            page = service.findByBtoA(mappingType, systemB, systemA, objectList, pageable);
        else if (ALL_MODE.equalsIgnoreCase(mode)) {
            page = service.findAllMapCoincidences(mappingType, systemA, systemB, objectList, pageable);
        }

        if (page != null) {
            List<MappingObjectDTO> mappinObjects = getMappingObjects(page.get());
            body.put("content", mappinObjects);
            addPageableAttributes(body, page);
            return ResponseEntity.ok(body);
        }
        /* Mensaje de error que indica que el contenido no fue encontrado */
        String documentation_url = "";
        String path = httpServletRequest.getRequestURI(); //+ " " + httpServletRequest.getRequestURL().toString();
        ApiError apiError;
        apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                path,
                xTraceId,
                documentation_url
        );
        apiError.setSuggestion(
                MessageFormat.format(
                        "Intente utilizando otros valores en los parámetros ''{0}'', ''{1}'', ''{2}'', ''{3}''",
                        MAPPING_TYPE_NAME, SYSTEM_A_NAME, SYSTEM_B_NAME, MODE_NAME
                )
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @GetMapping("/api/mappings/pages/{pageNumber}")
    public ResponseEntity<?> findAll(
            @RequestHeader(name = "x-organization-id") String xOrganizationId,
            @RequestHeader(name = "x-organization-type") String xOrganizationType,
            @RequestHeader(name = "x-client-application") String xClientApplication,
            @RequestHeader(name = "x-client-application-flow", required = false) String xClientApplicationFlow,
            @RequestHeader(name = "x-trace-id") String xTraceId,
            @RequestHeader(name = "x-user") String xUser,
            @PathVariable Integer pageNumber,
            @RequestParam Integer pageSize) {
        Map<String, Object> body = new LinkedHashMap<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Page.empty().getSort().ascending()); //Inicialización del paginado.
        Page<MapTable> page = service.findAll(pageable); //Obtiene absolutamente todos los registros de "map_table" (entidad MapTable).
        List<MappingObjectDTO> mappinObjects = getMappingObjects(page.get()); //transforma los objetos planos de la entidad MapTable a objetos MappingObjectDTO estructurados.
        
        body.put("content", mappinObjects);
        /*
         Siendo que no se va a devolver directamente un objeto Page del framework Spring Boot (sino que objetos 
         MappingObjectDTO), es necesario "reconstruir" en la respuesta del API los atributos que son propios del 
         mecanismo de paginación.
         */
        addPageableAttributes(body, page);
        return ResponseEntity.ok(body); //Devuelve un HTTP 200 (OK) con el cuerpo de objetos DTO + atributos con información de paginación.
    }

    /**
     * Retorna una lista de objetos <code>MappingObjectDTO</code> a partir de un stream de entidades JPA
     * <code>MapTable</code>.
     * @param  mapTables  Un stream de entidades JPA <code>MapTable</code>.
     * @return Una lista de objetos <code>MappingObjectDTO</code>.
     * @see MappingObjectDTO
     * @see MapTable
     */
    @NotNull
    private static List<MappingObjectDTO> getMappingObjects(Stream<MapTable> mapTables) {
        return mapTables
                .map(mt -> new MappingObjectDTO(
                        mt.getId(),
                        mt.getMappingType(),
                        new MapSystemDTO(mt.getSystemA(), mt.getSystemAType(), mt.getCodeA(), mt.getNameA(), "System A"),
                        new MapSystemDTO(mt.getSystemB(), mt.getSystemBType(), mt.getCodeB(), mt.getNameB(), "System B")
                )).toList();
    }

    private static void addPageableAttributes(Map<String, Object> body, Page<MapTable> page) {
        body.put("pageable", page.getPageable());
        body.put("totalElements", page.getTotalElements());
        body.put("totalPages", page.getTotalPages());
        body.put("last", page.isLast());
        body.put("size", page.getSize());
        body.put("number", page.getNumber());
        body.put("sort", page.getSort());
        body.put("numberOfElements", page.getNumberOfElements());
        body.put("first", page.isFirst());
        body.put("empty", page.isEmpty());
    }

    private static void logMessage(String methodName, Map<String, String> headers, String xOrganizationId, String xOrganizationType, String xClientApplication, String xClientApplicationFlow, String xTraceId, String xUser, String mappingType, String systemA, String systemB, String code, String mode) {
        String message = MessageFormat
                .format("Method name: " + methodName + "; " +
                                "Params:" + MAPPING_TYPE_NAME + ": ''{0}'', " + SYSTEM_A_NAME + ": ''{1}'', " + CODE_NAME + ": ''{2}'', " +
                                SYSTEM_B_NAME + ": ''{3}'', " + MODE_NAME + ": ''{4}''; ",
                        mappingType, systemA, code, systemB, mode);
        log.info(headers.toString() + " " + message);
    }

    @NotNull
    private ApiError createResourceNotFoundError(String xTraceId, String documentation_url, String ... parameters) {
        String path = httpServletRequest.getRequestURI();
        StringBuilder suggestion = new StringBuilder("Intente utilizando otros valores en los parámetros: ");
        for (String parameter : parameters)
            suggestion.append("'").append(parameter).append("',");

        suggestion = new StringBuilder(suggestion.substring(0, suggestion.length() - 1));

        ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                path,
                xTraceId,
                documentation_url
        );
        apiError.setSuggestion(suggestion.toString());
        return apiError;
    }
}
