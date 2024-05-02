package idatt2106.systemutvikling.sparesti.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model class for the PSUConsent entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PSUConsent {

    String consentId;
    String psuId;
    String validUntil;

    boolean accessAccounts;
    boolean accessBalances;
    boolean accessTransactions;
}
