package info.zamojski.soft.towercollector.collector.validators;

import info.zamojski.soft.towercollector.collector.validators.specific.CdmaCellValidator;
import info.zamojski.soft.towercollector.collector.validators.specific.GsmCellValidator;
import info.zamojski.soft.towercollector.collector.validators.specific.LteCellValidator;
import info.zamojski.soft.towercollector.collector.validators.specific.NrCellValidator;
import info.zamojski.soft.towercollector.collector.validators.specific.TdscdmaCellValidator;
import info.zamojski.soft.towercollector.collector.validators.specific.WcdmaCellValidator;

public abstract class CellValidatorBase {

    // Eagerly created, never reassigned: the validators are stateless and their constructors do
    // no work, so a lazily-initialized field only added a write under the caller's control flow.
    private final GsmCellValidator gsmValidator = new GsmCellValidator();
    private final WcdmaCellValidator wcdmaValidator = new WcdmaCellValidator();
    private final LteCellValidator lteValidator = new LteCellValidator();
    private final CdmaCellValidator cdmaValidator = new CdmaCellValidator();
    private final NrCellValidator nrValidator = new NrCellValidator();
    private final TdscdmaCellValidator tdscdmaValidator = new TdscdmaCellValidator();

    protected GsmCellValidator getGsmValidator() {
        return gsmValidator;
    }

    protected WcdmaCellValidator getWcdmaValidator() {
        return wcdmaValidator;
    }

    protected LteCellValidator getLteValidator() {
        return lteValidator;
    }

    protected CdmaCellValidator getCdmaValidator() {
        return cdmaValidator;
    }

    protected NrCellValidator getNrValidator() {
        return nrValidator;
    }

    protected TdscdmaCellValidator getTdscdmaValidator() {
        return tdscdmaValidator;
    }
}
