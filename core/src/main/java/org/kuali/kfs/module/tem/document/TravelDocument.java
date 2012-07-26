/*
 * Copyright 2010 The Kuali Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kfs.module.tem.document;

import java.sql.Timestamp;
import java.util.List;

import org.kuali.kfs.fp.document.DisbursementVoucherDocument;
import org.kuali.kfs.integration.tem.TravelEntertainmentMovingTravelDocument;
import org.kuali.kfs.module.tem.businessobject.ActualExpense;
import org.kuali.kfs.module.tem.businessobject.GroupTraveler;
import org.kuali.kfs.module.tem.businessobject.HistoricalTravelExpense;
import org.kuali.kfs.module.tem.businessobject.ImportedExpense;
import org.kuali.kfs.module.tem.businessobject.PerDiemExpense;
import org.kuali.kfs.module.tem.businessobject.PrimaryDestination;
import org.kuali.kfs.module.tem.businessobject.SpecialCircumstances;
import org.kuali.kfs.module.tem.businessobject.TEMExpense;
import org.kuali.kfs.module.tem.businessobject.TEMProfile;
import org.kuali.kfs.module.tem.businessobject.TransportationModeDetail;
import org.kuali.kfs.module.tem.businessobject.TravelAdvance;
import org.kuali.kfs.module.tem.businessobject.TravelerDetail;
import org.kuali.kfs.module.tem.businessobject.TripType;
import org.kuali.kfs.module.tem.service.AccountingDistributionService;
import org.kuali.kfs.sys.businessobject.SourceAccountingLine;
import org.kuali.kfs.sys.document.AccountingDocument;
import org.kuali.rice.kns.util.KualiDecimal;

/**
 * Abstract Travel Document Base
 */
public interface TravelDocument extends AccountingDocument, TravelEntertainmentMovingTravelDocument {

    /**
     * Determines if this document should be able to return to the fiscal officer node again. This can happen
     * if the user has rights to reroute and also if the document is already ENROUTE.
     * 
     * @return true if the doucment is currently enroute and reroutable
     */
    boolean canReturn();

    void initiateDocument();

    @Override
    String getTravelDocumentIdentifier();

    void setTravelDocumentIdentifier(String travelDocumentIdentifier);

    String getAppDocStatus();

    Integer getTravelDocumentLinkIdentifier();

    void setTravelDocumentLinkIdentifier(Integer travelDocumentLinkIdentifier);

    @Override
    /**
     * Gets the traveler attribute.
     * 
     * @return Returns the traveler.
     */
    TravelerDetail getTraveler();

    /**
     * Sets the traveler attribute value.
     * 
     * @param traveler The traveler to set.
     */
    void setTraveler(TravelerDetail traveler);


    /**
     * Gets the travelerDetailId attribute.
     * 
     * @return Returns the travelerDetailId.
     */
    Integer getTravelerDetailId();

    /**
     * Sets the travelerDetailId attribute value.
     * 
     * @param travelerDetailId The travelerDetailId to set.
     */
    void setTravelerDetailId(Integer travelerDetailId);

    /**
     * This method sets the trip description for this request
     * 
     * @param tripDescription
     */
    void setTripDescription(String tripDescription);

    Integer getPrimaryDestinationId();

    void setPrimaryDestinationId(Integer primaryDestinationId);

    PrimaryDestination getPrimaryDestination();

    void setPrimaryDestination(PrimaryDestination primaryDestination);
    
    @Override
    String getPrimaryDestinationName();
    
    void setPrimaryDestinationName(String primaryDestinationName);
    
    String getPrimaryDestinationCountryState();
    
    void setPrimaryDestinationCountryState(String primaryDestinationCountryState);
    
    String getPrimaryDestinationCounty();
    
    void setPrimaryDestinationCounty(String primaryDestinationCounty);
    
    Boolean getPrimaryDestinationIndicator();
    
    void setPrimaryDestinationIndicator(Boolean primaryDestinationIndicator);
    
    /**
     * This method returns the trip type associated with this Travel Request document
     * 
     * @return trip type code
     */
    TripType getTripType();

    /**
     * This method sets the trip type should only be used by the ojb retrieval
     * 
     * @param tripType
     */
    void setTripType(TripType tripType);

    /**
     * This method returns the trip type code associated with the travel request document
     * 
     * @return trip type code
     */
    String getTripTypeCode();

    /**
     * This method returns the trip type code for this travel request document
     * 
     * @param tripTypeCode
     */
    void setTripTypeCode(String tripTypeCode);

    @Override
    /**
     * This method gets the begin date for this trip
     * 
     * @return trip begin date
     */
    Timestamp getTripBegin();

    /**
     * This method sets the trip begin date for this request
     * 
     * @param tripBegin
     */
    void setTripBegin(Timestamp tripBegin);

    /**
     * This method returns the trip end date for this request
     * 
     * @return trip end date
     */
    Timestamp getTripEnd();


    /**
     * This method sets the trip end date for this request
     * 
     * @param tripEnd
     */
    void setTripEnd(Timestamp tripEnd);

    KualiDecimal getExpenseLimit();

    void setExpenseLimit(KualiDecimal expenseLimit);

    void setSpecialCircumstances(final List<SpecialCircumstances> specialCircumstances);

    List<SpecialCircumstances> getSpecialCircumstances();

    List<PerDiemExpense> getPerDiemExpenses();

    void setPerDiemExpenses(List<PerDiemExpense> perDiemExpenses);

    KualiDecimal getEncumbranceTotal();

    void enableExpenseTypeSpecificFields(final List<ActualExpense> actualExpenses);

    KualiDecimal getTotalPendingAmount(ActualExpense actualExpense);

    KualiDecimal getParentExpenseAmount(List<ActualExpense> actualExpenses, Long id);

    KualiDecimal getTotalDetailExpenseAmount(ActualExpense actualExpense);

    ActualExpense getParentExpenseRecord(List<ActualExpense> actualExpenses, Long id);

    KualiDecimal getActualExpensesTotal();

    void addActualExpense(final ActualExpense line);

    void removeActualExpense(final Integer index);
    
    String getDelinquentAction();
    
    boolean canDisplayAgencySitesUrl();

    String getAgencySitesUrl();
    
    boolean canPassTripIdToAgencySites();
    
    /**
     * 
     * This method provides the same getter call for travel doc and tem profile's profileId.
     * @return
     */
    Integer getProfileId();

    /**
     * 
     * This method provides additional support to populate profile.
     * @return
     */
    void setProfileId(Integer profileId);
    
    Integer getTemProfileId();

    void setTemProfileId(Integer temProfileId);

    TEMProfile getTemProfile();

    /**
     * Sets the temProfile attribute value.
     * @param temProfile The temProfile to set.
     */
    void setTemProfile(TEMProfile temProfile);
    List<TransportationModeDetail> getTransportationModes();

    void setTransportationModes(List<TransportationModeDetail> transportationModes);
    
    List<GroupTraveler> getGroupTravelers();
    
    void setGroupTravelers(List<GroupTraveler> groupTravelers);
    
    List<TravelAdvance> getTravelAdvances();
    
    void setTravelAdvances(List<TravelAdvance> travelAdvances);
    
    List<ActualExpense> getActualExpenses();
    
    void setActualExpenses(List<ActualExpense> actualExpenses);
    
    List<ImportedExpense> getImportedExpenses();
    
    void setImportedExpenses(List<ImportedExpense> importedExpenses);
    
    KualiDecimal getTotalFor(final String financialObjectCode);

    KualiDecimal getDocumentGrandTotal();
    
    KualiDecimal getDailyTotalGrandTotal();
    
    KualiDecimal getReimbursableTotal();
    
    KualiDecimal getNonReimbursableTotal();
    
    KualiDecimal getApprovedAmount();
    
    void addExpense(TEMExpense line);
    
    void addExpenseDetail(TEMExpense line, Integer index);

    void removeExpense(TEMExpense line, Integer index);
    
    void removeExpenseDetail(TEMExpense line, Integer index);
    
    KualiDecimal getCTSTotal();
    
    KualiDecimal getCorporateCardTotal();
    
    AccountingDistributionService getAccountingDistributionService();
    
    List<HistoricalTravelExpense> getHistoricalTravelExpenses();

    void setHistoricalTravelExpenses(List<HistoricalTravelExpense> historicalTravelExpenses);

    String getMealWithoutLodgingReason();   
    
    String getDocumentTypeName();
    
    String getReportPurpose();
    
    void populateVendorPayment(DisbursementVoucherDocument disbursementVoucherDocument);
    
    KualiDecimal getPerDiemAdjustment();

    void setPerDiemAdjustment(KualiDecimal perDiemAdjustment);
    
    /**
     * Populate the fields from the Travel document to create the DisbursementVoucherDocument
     * 
     * @param disbursementVoucherDocument
     */
    void populateDisbursementVoucherFields(DisbursementVoucherDocument disbursementVoucherDocument);
    
    /**
     * Return the source accounting lines which will be used for reimbursement in DV
     * 
     * @return
     */
    List<SourceAccountingLine> getReimbursableSourceAccountingLines();
    
    /**
     * Return default card agency type in the source accounting line
     * 
     * @return
     */
    String getDefaultAccountingLineCardAgencyType();

    /**
     * Return the expense type code by Travel Document
     * 
     * TA document returns EMCUMBRANCE
     * TEMReimbursement document returns OUT OF POCKET
     * 
     * @return
     */
    String getExpenseTypeCode();

    /**
     * Return true if the travel document has custom distribution for the DV doc
     * 
     * @return
     */
    boolean hasCustomDVDistribution();

    /**
     * Adds a {@link GroupTraveler} instance to the {@link TravelDocument}. Handles all the
     * under-the-hood stuff like setting the documentnumber.
     * 
     * @param traveler {@link GroupTraveler} instance that is valid
     */
    void addGroupTraveler(final GroupTraveler traveler);
}
