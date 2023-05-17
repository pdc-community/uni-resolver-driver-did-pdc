package uniresolver.driver.did.pdc;

import com.vnet.did.protocol.base.PublicKeyProperty;
import com.vnet.did.protocol.base.ServiceProperty;
import foundation.identity.did.Service;
import foundation.identity.did.VerificationMethod;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description
 * @Author wqq
 * @Date 2023/5/10 11:58
 */
public class PdcDidDocument {

    public static final String CONTEXT_URL = "https://w3id.org/did/v1";

    private String context = CONTEXT_URL;
    private String did;
    private List<Service> serviceList;
    private List<VerificationMethod> verificationMethodList;

    public String getContext() {
        return context;
    }
    public String getDid() {
        return did;
    }
    public List<Service> getServiceList() {
        return serviceList;
    }
    public List<VerificationMethod> getVerificationMethodList() {
        return verificationMethodList;
    }

    public PdcDidDocument setContext(String context) {
        this.context = context;
        return this;
    }

    public PdcDidDocument setDid(String did) {
        this.did = did;
        return this;
    }

    public PdcDidDocument setServiceList(List<ServiceProperty> servicePropertyList) {
        List<Service> serviceList = new LinkedList();
        if (CollectionUtils.isNotEmpty(servicePropertyList)) {
            for (ServiceProperty s : servicePropertyList) {
                Service service = Service.builder()
                        .serviceEndpoint(s.getServiceEndpoint())
                        .build();
                serviceList.add(service);
            }
        }
        this.serviceList = serviceList;
        return this;
    }

    public PdcDidDocument setVerificationMethodList(List<PublicKeyProperty> publicKeyList) {
        List<VerificationMethod> verificationMethodList= new LinkedList();
        if (CollectionUtils.isNotEmpty(publicKeyList)) {
            for (PublicKeyProperty p : publicKeyList) {
                VerificationMethod verificationMethod = VerificationMethod.builder()
                        .publicKeyBase64(Base64.getEncoder().encodeToString(p.getPublicKey().getBytes()))
                        .publicKeyMultibase(p.getId())
                        .build();
                verificationMethodList.add(verificationMethod);
            }
        }
        this.verificationMethodList = verificationMethodList;
        return this;
    }

}
