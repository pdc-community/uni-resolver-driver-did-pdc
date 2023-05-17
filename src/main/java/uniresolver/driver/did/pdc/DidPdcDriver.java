package uniresolver.driver.did.pdc;

import com.vnet.did.protocol.base.PIdDocument;
import com.vnet.did.protocol.response.ResponseData;
import com.vnet.did.service.DidService;
import com.vnet.did.service.impl.DidServiceImpl;
import com.vnet.did.util.DidUtils;
import foundation.identity.did.DIDDocument;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uniresolver.ResolutionException;
import uniresolver.driver.Driver;
import uniresolver.result.ResolveResult;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description
 * @Author wqq
 * @Date 2023/5/8 14:15
 */
public class DidPdcDriver implements Driver {

    private static final Logger log = LoggerFactory.getLogger(DidPdcDriver.class);

    private final Map<String, Object> properties;

    public DidPdcDriver(Map<String, Object> properties) {
        this.properties = properties;
    }

    public DidPdcDriver() {
        this(Map.of());

    }

    @Override
    public ResolveResult resolve(String identifiers) throws ResolutionException {
        if (!didIsValid(identifiers)) return null;
        String did = identifiers;
        PIdDocument pidDocument = getPidDocument(did);
        ResolveResult resolveResult = buildResolveResult(pidDocument);
        return resolveResult;
    }

    public boolean didIsValid(String did) {
        return DidUtils.isPIdValid(did);
    }

    private PIdDocument getPidDocument(String did) throws ResolutionException {
        DidService didService = new DidServiceImpl();
        ResponseData<PIdDocument> pidDocument = didService.getDidDocument(did);
        Integer errorCode = pidDocument.getErrorCode();
        if (!StringUtils.equals("0", errorCode.toString())) {
            log.error("get did document failed caused by {}", pidDocument.getErrorMessage());
            throw new ResolutionException("get did document failed");
        }
        PIdDocument pIdDocument = pidDocument.getResult();
        return pIdDocument;
    }

    public ResolveResult buildResolveResult(PIdDocument pIdDocument) {
        PdcDidDocument pdcDidDocument = new PdcDidDocument()
                .setDid(pIdDocument.getId())
                .setServiceList(pIdDocument.getService())
                .setVerificationMethodList(pIdDocument.getPublicKey());
        DIDDocument didDocument = buildDidDocument(pdcDidDocument);

        PdcDidResolverMethodMetaData pdcMethodMetadata = new PdcDidResolverMethodMetaData()
                .setCreated(pIdDocument.getCreated())
                .setUpdated(pIdDocument.getUpdated());
        Map<String, Object> methodMetadata = buildMethodMetadata(pdcMethodMetadata);

        ResolveResult resolveResult = ResolveResult.build(didDocument, null, methodMetadata);
        return resolveResult;
    }

    private DIDDocument buildDidDocument(PdcDidDocument pdcDidDocument) {
        DIDDocument didDocument = DIDDocument.builder()
                .context(URI.create(pdcDidDocument.getContext()))
                .services(pdcDidDocument.getServiceList())
                .id(URI.create(pdcDidDocument.getDid()))
                .verificationMethods(pdcDidDocument.getVerificationMethodList())
                .build();
        return didDocument;
    }

    private Map<String, Object> buildMethodMetadata(PdcDidResolverMethodMetaData metaData) {
        Map<String, Object> methodMetadata = new LinkedHashMap<>();
        methodMetadata.put("created", metaData.getCreated());
        methodMetadata.put("updated", metaData.getUpdated());
        return methodMetadata;
    }


    @Override
    public Map<String, Object> properties() {
        return getProperties();
    }

    public Map<String, Object> getProperties() {
        return properties;
    }


}
