package uniresolver.driver.did.pdc;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.vnet.did.protocol.base.PublicKeyProperty;
import com.vnet.did.protocol.base.ServiceProperty;
import foundation.identity.did.Service;
import foundation.identity.did.VerificationMethod;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @Description
 * @Author wqq
 * @Date 2023/5/10 11:58
 */
public class PdcDidResolverMethodMetaData {

    private String created = "";
    private String updated = "";

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public PdcDidResolverMethodMetaData setCreated(Long created) {
        if(Objects.nonNull(created)){
            this.created = LocalDateTimeUtil.of(created*1000).toString();
        }
        return this;
    }


    public PdcDidResolverMethodMetaData setUpdated(Long updated) {
        if(Objects.nonNull(updated)){
            this.updated = LocalDateTimeUtil.of(updated).toString();
        }
        return this;
    }


}