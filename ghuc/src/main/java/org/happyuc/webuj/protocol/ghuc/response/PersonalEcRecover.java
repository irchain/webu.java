package org.happyuc.webuj.protocol.ghuc.response;

import org.happyuc.webuj.protocol.core.Response;

/**
 * personal_ecRecover.
 */
public class PersonalEcRecover extends Response<String> {
    public String getRecoverAccountId() {
        return getResult();
    }
}
