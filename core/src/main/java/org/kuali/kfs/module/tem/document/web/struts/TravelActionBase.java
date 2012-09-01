/*
 * Copyright 2011 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl2.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kfs.module.tem.document.web.struts;

import static org.kuali.kfs.module.tem.TemConstants.CERTIFICATION_STATEMENT_ATTRIBUTE;
import static org.kuali.kfs.module.tem.TemConstants.DELINQUENT_TEST_ATTRIBUTE;
import static org.kuali.kfs.module.tem.TemConstants.EMPLOYEE_TEST_ATTRIBUTE;
import static org.kuali.kfs.module.tem.TemConstants.FISCAL_OFFICER_TEST_ATTRIBUTE;
import static org.kuali.kfs.module.tem.TemConstants.PARAM_NAMESPACE;
import static org.kuali.kfs.module.tem.TemConstants.PRIMARY_DESTINATION_LOOKUPABLE;
import static org.kuali.kfs.module.tem.TemConstants.RETURN_TO_FO_QUESTION;
import static org.kuali.kfs.module.tem.TemConstants.SHOW_ACCOUNT_DISTRIBUTION_ATTRIBUTE;
import static org.kuali.kfs.module.tem.TemConstants.SHOW_ADVANCES_ATTRIBUTE;
import static org.kuali.kfs.module.tem.TemConstants.SHOW_REPORTS_ATTRIBUTE;
import static org.kuali.kfs.module.tem.TemConstants.TRAVEL_ARRANGER_TEST_ATTRIBUTE;
import static org.kuali.kfs.module.tem.TemConstants.TRAVEL_MANAGER_TEST_ATTRIBUTE;
import static org.kuali.kfs.module.tem.TemConstants.TravelParameters.DOCUMENT_DTL_TYPE;
import static org.kuali.kfs.module.tem.TemConstants.TravelParameters.EMPLOYEE_CERTIFICATION_STATEMENT;
import static org.kuali.kfs.module.tem.TemConstants.TravelParameters.NON_EMPLOYEE_CERTIFICATION_STATEMENT;
import static org.kuali.kfs.module.tem.TemConstants.TravelReimbursementParameters.DISPLAY_ADVANCES_IN_REIMBURSEMENT_TOTAL_IND;
import static org.kuali.kfs.module.tem.TemConstants.TravelReimbursementParameters.ENABLE_ACCOUNTING_DISTRIBUTION_TAB_IND;
import static org.kuali.kfs.module.tem.TemPropertyConstants.TRIP_INFO_UPDATE_TRIP_DTL;
import static org.kuali.kfs.module.tem.util.BufferedLogger.debug;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.kuali.kfs.module.tem.TemConstants;
import org.kuali.kfs.module.tem.TemConstants.TravelAuthorizationParameters;
import org.kuali.kfs.module.tem.TemConstants.TravelEntertainmentParameters;
import org.kuali.kfs.module.tem.TemConstants.TravelReimbursementParameters;
import org.kuali.kfs.module.tem.TemConstants.TravelRelocationParameters;
import org.kuali.kfs.module.tem.TemKeyConstants;
import org.kuali.kfs.module.tem.TemPropertyConstants;
import org.kuali.kfs.module.tem.TemPropertyConstants.TEMProfileProperties;
import org.kuali.kfs.module.tem.TemWorkflowConstants;
import org.kuali.kfs.module.tem.businessobject.AccountingDocumentRelationship;
import org.kuali.kfs.module.tem.businessobject.ActualExpense;
import org.kuali.kfs.module.tem.businessobject.GroupTraveler;
import org.kuali.kfs.module.tem.businessobject.HistoricalTravelExpense;
import org.kuali.kfs.module.tem.businessobject.ImportedExpense;
import org.kuali.kfs.module.tem.businessobject.PerDiem;
import org.kuali.kfs.module.tem.businessobject.PerDiemExpense;
import org.kuali.kfs.module.tem.businessobject.PrimaryDestination;
import org.kuali.kfs.module.tem.businessobject.SpecialCircumstances;
import org.kuali.kfs.module.tem.businessobject.TEMProfile;
import org.kuali.kfs.module.tem.businessobject.TemDistributionAccountingLine;
import org.kuali.kfs.module.tem.businessobject.TemSourceAccountingLine;
import org.kuali.kfs.module.tem.businessobject.TravelerDetail;
import org.kuali.kfs.module.tem.document.TravelAuthorizationDocument;
import org.kuali.kfs.module.tem.document.TravelDocument;
import org.kuali.kfs.module.tem.document.TravelDocumentBase;
import org.kuali.kfs.module.tem.document.TravelEntertainmentDocument;
import org.kuali.kfs.module.tem.document.TravelReimbursementDocument;
import org.kuali.kfs.module.tem.document.TravelRelocationDocument;
import org.kuali.kfs.module.tem.document.authorization.ReturnToFiscalOfficerAuthorizer;
import org.kuali.kfs.module.tem.document.service.AccountingDocumentRelationshipService;
import org.kuali.kfs.module.tem.document.service.TravelDocumentService;
import org.kuali.kfs.module.tem.document.service.TravelEncumbranceService;
import org.kuali.kfs.module.tem.document.validation.event.AddGroupTravelLineEvent;
import org.kuali.kfs.module.tem.document.validation.event.RecalculateTripDetailTotalEvent;
import org.kuali.kfs.module.tem.document.validation.event.UpdateTripDetailsEvent;
import org.kuali.kfs.module.tem.document.web.bean.AccountingDistribution;
import org.kuali.kfs.module.tem.document.web.bean.TravelMvcWrapperBean;
import org.kuali.kfs.module.tem.exception.UploadParserException;
import org.kuali.kfs.module.tem.report.service.TravelReportService;
import org.kuali.kfs.module.tem.service.AccountingDistributionService;
import org.kuali.kfs.module.tem.service.TravelerService;
import org.kuali.kfs.module.tem.util.ExpenseUtils;
import org.kuali.kfs.module.tem.util.UploadParser;
import org.kuali.kfs.sys.KFSConstants;
import org.kuali.kfs.sys.KFSKeyConstants;
import org.kuali.kfs.sys.KFSPropertyConstants;
import org.kuali.kfs.sys.businessobject.AccountingLine;
import org.kuali.kfs.sys.businessobject.GeneralLedgerPendingEntry;
import org.kuali.kfs.sys.businessobject.SourceAccountingLine;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.kfs.sys.service.SegmentedLookupResultsService;
import org.kuali.kfs.sys.web.struts.KualiAccountingDocumentActionBase;
import org.kuali.kfs.sys.web.struts.KualiAccountingDocumentFormBase;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.service.PersonService;
import org.kuali.rice.kim.util.KIMPropertyConstants;
import org.kuali.rice.kns.bo.PersistableBusinessObject;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.DocumentService;
import org.kuali.rice.kns.service.KualiRuleService;
import org.kuali.rice.kns.service.ParameterService;
import org.kuali.rice.kns.util.ErrorMessage;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.rice.kns.util.KNSConstants;
import org.kuali.rice.kns.util.KNSPropertyConstants;
import org.kuali.rice.kns.util.KualiDecimal;
import org.kuali.rice.kns.util.ObjectUtils;
import org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase;

import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.SimpleBookmark;

/**
 * Common action class
 */
public abstract class TravelActionBase extends KualiAccountingDocumentActionBase {

    protected static final String[] methodToCallExclusionArray = { "recalculate", "calculate", "recalculateTripDetailTotal", "save", "route", "approve", "blanketApprove", "updatePerDiemExpenses" };
    public static final String[] GROUP_TRAVELER_ATTRIBUTE_NAMES = { "travelerTypeCode", "groupTravelerEmpId", "name" };


    protected DocumentService getDocumentService() {
        return SpringContext.getBean(DocumentService.class);
    }

    protected TravelDocumentService getTravelDocumentService() {
        return SpringContext.getBean(TravelDocumentService.class);
    }
    
    protected TravelEncumbranceService getTravelEncumbranceService() {
        return SpringContext.getBean(TravelEncumbranceService.class);
    }

    public PersonService<Person> getPersonService() {
        return SpringContext.getBean(PersonService.class);
    }

    protected TravelReportService getTravelReportService() {
        return SpringContext.getBean(TravelReportService.class);
    }

    public AccountingDocumentRelationshipService getAccountingDocumentRelationshipService() {
        return SpringContext.getBean(AccountingDocumentRelationshipService.class);
    }
    
    protected TravelerService getTravelerService() {
        return SpringContext.getBean(TravelerService.class);
    }

    protected AccountingDistributionService getAccountingDistributionService() {
        return SpringContext.getBean(AccountingDistributionService.class);
    }
    
    /**
     * When the approver only wants the accounting lines to be changed but the trip information is acceptable, routes the document
     * back to the Account Node (Fiscal Officer Reviewer) and removes the fiscal officer approvals and the approvals that are beyond
     * the Account node. The fiscal officer should be able to select the accounts outside of their organization like the PCDO
     * document.
     */
    public ActionForward returnToFiscalOfficer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        debug("Returning to Fiscal Officer");
        final QuestionHandler<TravelDocument> questionHandler = getQuestionHandler(RETURN_TO_FO_QUESTION);

        final Inquisitive<TravelDocument, ActionForward> inq = new StrutsInquisitor<TravelDocument, TravelFormBase, TravelActionBase>(mapping, (TravelFormBase) form, this, request, response);

        if (inq.wasQuestionAsked()) {
            return questionHandler.handleResponse(inq);
        }
        
        return questionHandler.askQuestion(inq);
    }

    protected <T> T getQuestionHandler(final String question) {
        final T retval = (T) SpringContext.getService(question);
        
        return retval;
    }

    /**
     * For use with a specific set of methods of this class that create new purchase order-derived document types in response to
     * user actions, including <code>amendTa</code>. It employs the question framework to ask the user for a response before
     * creating and routing the new document. The response should consist of a note detailing a reason, and either yes or no. This
     * method can be better understood if it is noted that it will be gone through twice (via the question framework); when each
     * question is originally asked, and again when the yes/no response is processed, for confirmation.
     * 
     * @param mapping These are boiler-plate.
     * @param form "
     * @param request "
     * @param response "
     * @param questionType A string identifying the type of question being asked.
     * @param confirmType A string identifying which type of question is being confirmed.
     * @param documentType A string, the type of document to create
     * @param notePrefix A string to appear before the note in the BO Notes tab
     * @param messageType A string to appear on the PO once the question framework is done, describing the action taken
     * @param operation A string, the verb to insert in the original question describing the action to be taken
     * @return An ActionForward
     * @throws Exception
     */
    protected ActionForward askQuestionsAndPerformDocumentAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String questionType, String confirmType, String documentType, String notePrefix, String messageType, String operation) throws Exception {
        debug("askQuestionsAndPerformDocumentAction started.");
        TravelFormBase trForm = (TravelFormBase) form;
        TravelDocument document = trForm.getTravelDocument();
        
        return null;
    }

    @Override
    public ActionForward route(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        recalculateTripDetailTotalOnly(mapping, form, request, response);
        
        TravelFormBase travelForm = (TravelFormBase) form;

        if (requiresCalculate(travelForm)) {
            GlobalVariables.getMessageMap().putError(KFSConstants.DOCUMENT_ERRORS, TemKeyConstants.ERROR_REQUIRES_CALCULATE, new String[] { "routing" });

            return mapping.findForward(KFSConstants.MAPPING_BASIC);
        }

        ActionForward forward = super.route(mapping, travelForm, request, response);
        getTravelDocumentService().attachImportedExpenses(travelForm.getTravelDocument());
        
        return forward;
    }

    /**
     * Determines whether or not someone can amend a travel authorization
     * 
     * @param reqForm
     */
    protected void setCanReturn(TravelFormBase reqForm) {
        boolean can = reqForm.getTravelDocument().canReturn();

        if (can) {
            ReturnToFiscalOfficerAuthorizer documentAuthorizer = getDocumentAuthorizer(reqForm);
            String documentTypeName = getDataDictionaryService().getDocumentTypeNameByClass(reqForm.getTravelDocument().getClass());
            can = documentAuthorizer.canReturn(reqForm.getTravelDocument(), GlobalVariables.getUserSession().getPerson());
        }

        reqForm.setCanReturn(can);
    }

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GlobalVariables.getUserSession().removeObject(TemPropertyConstants.TEMProfileProperties.PROFILE_ID);
        GlobalVariables.getUserSession().removeObject(KFSPropertyConstants.DOCUMENT_TYPE_CODE);
        TravelFormBase travelFormBase = (TravelFormBase) form;
        final String methodToCall = travelFormBase.getMethodToCall();
        final TravelDocument document = (TravelDocument) travelFormBase.getDocument();
        document.refreshReferenceObject(TemPropertyConstants.TRIP_TYPE);
        String showPerDiemBreakdown = getParameterService().getParameterValue(PARAM_NAMESPACE, TravelAuthorizationParameters.PARAM_DTL_TYPE, TravelAuthorizationParameters.ENABLE_TA_PER_DIEM_AMOUNT_EDIT_IND);
        travelFormBase.setShowPerDiemBreakdown(showPerDiemBreakdown != null && showPerDiemBreakdown.equals(KFSConstants.ParameterValues.YES));
        travelFormBase.setDisplayNonEmployeeForm(!isEmployee(document.getTraveler()));

        if (!StringUtils.isEmpty(methodToCall) && !methodToCall.equalsIgnoreCase("docHandler")) {
            // Setting the certification statement in the case that there is a validation error
            request.setAttribute(CERTIFICATION_STATEMENT_ATTRIBUTE, getCertificationStatement(document));
            request.setAttribute(EMPLOYEE_TEST_ATTRIBUTE, isEmployee(document.getTraveler()));
            request.setAttribute(TRAVEL_ARRANGER_TEST_ATTRIBUTE, setTravelArranger((TravelFormBase) form));
            request.setAttribute(TRAVEL_MANAGER_TEST_ATTRIBUTE, setTravelManager((TravelFormBase) form));
            request.setAttribute(FISCAL_OFFICER_TEST_ATTRIBUTE, setFiscalOfficer((TravelFormBase) form));
            request.setAttribute(DELINQUENT_TEST_ATTRIBUTE, document.getDelinquentAction());
        }
        // if (travelFormBase.getTravelDocument().getSourceAccountingLines() != null){
        // Collections.sort(travelFormBase.getTravelDocument().getSourceAccountingLines(), new SourceAccountingLineComparator());
        // }
        
        ExpenseUtils.calculateMileage(document.getActualExpenses());
        
        final ActionForward retval = super.execute(mapping, form, request, response);
        request.setAttribute(CERTIFICATION_STATEMENT_ATTRIBUTE, getCertificationStatement(document));
        request.setAttribute(EMPLOYEE_TEST_ATTRIBUTE, isEmployee(document.getTraveler()));
        request.setAttribute(TRAVEL_ARRANGER_TEST_ATTRIBUTE, setTravelArranger((TravelFormBase) form));
        request.setAttribute(TRAVEL_MANAGER_TEST_ATTRIBUTE, setTravelManager((TravelFormBase) form));
        request.setAttribute(FISCAL_OFFICER_TEST_ATTRIBUTE, setFiscalOfficer((TravelFormBase) form));
        request.setAttribute(DELINQUENT_TEST_ATTRIBUTE, document.getDelinquentAction());

        final Map<String, List<Document>> relatedDocuments = getTravelDocumentService().getDocumentsRelatedTo(document);
        travelFormBase.setRelatedDocuments(relatedDocuments);
        travelFormBase.setDistribution(getAccountingDistributionService().buildDistributionFrom(travelFormBase.getTravelDocument()));
        
        //reset accounting line sequence number
        if (document.getSourceAccountingLines().size() > 0){
            int counter = 1;
            for (SourceAccountingLine line : (List<SourceAccountingLine>)document.getSourceAccountingLines()){
                line.setSequenceNumber(new Integer(counter));
                counter++;
            }
        }
        
        return retval;
    }

    @Override
    public ActionForward refresh(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.refresh(mapping, form, request, response);
        TravelFormBase travelForm = (TravelFormBase) form;

        Collection<PersistableBusinessObject> rawValues = null;
        Map<String, Set<String>> segmentedSelection = new HashMap<String, Set<String>>();

        // If multiple asset lookup was used to select the assets, then....
        if (StringUtils.equals(KFSConstants.MULTIPLE_VALUE, travelForm.getRefreshCaller())) {
            String lookupResultsSequenceNumber = travelForm.getLookupResultsSequenceNumber();

            if (StringUtils.isNotBlank(lookupResultsSequenceNumber)) {
                // actually returning from a multiple value lookup
                Set<String> selectedIds = SpringContext.getBean(SegmentedLookupResultsService.class).retrieveSetOfSelectedObjectIds(lookupResultsSequenceNumber, GlobalVariables.getUserSession().getPerson().getPrincipalId());
                for (String selectedId : selectedIds) {
                    String selectedObjId = StringUtils.substringBefore(selectedId, ".");
                    String selectedMonthData = StringUtils.substringAfter(selectedId, ".");

                    if (!segmentedSelection.containsKey(selectedObjId)) {
                        segmentedSelection.put(selectedObjId, new HashSet<String>());
                    }
                    segmentedSelection.get(selectedObjId).add(selectedMonthData);
                }
                // Retrieving selected data from table.
                LOG.debug("Asking segmentation service for object ids " + segmentedSelection.keySet());
                rawValues = SpringContext.getBean(SegmentedLookupResultsService.class).retrieveSelectedResultBOs(lookupResultsSequenceNumber, segmentedSelection.keySet(), HistoricalTravelExpense.class, GlobalVariables.getUserSession().getPerson().getPrincipalId());
                List<ImportedExpense> newImportedExpenses = ExpenseUtils.convertHistoricalToImportedExpense((List) rawValues, travelForm.getTravelDocument());
                AddImportedExpenseEvent event = new AddImportedExpenseEvent();
                for (ImportedExpense newImportedExpense : newImportedExpenses) {
                    travelForm.setNewImportedExpenseLine(newImportedExpense);
                    event.update(null, travelForm);
                }
                if (rawValues != null && rawValues.size() > 0) {
                    this.save(mapping, travelForm, request, response);
                    GlobalVariables.getMessageList().add(TemKeyConstants.INFO_TEM_IMPORT_DOCUMENT_SAVE);
                }
            }
        }
        
        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    @Override
    public ActionForward performLookup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase travelForm = (TravelFormBase) form;
        // parse out the important strings from our methodToCall parameter
        String fullParameter = (String) request.getAttribute(KNSConstants.METHOD_TO_CALL_ATTRIBUTE);
        validateLookupInquiryFullParameter(request, form, fullParameter);
        String boClassName = StringUtils.substringBetween(fullParameter, KNSConstants.METHOD_TO_CALL_BOPARM_LEFT_DEL, KNSConstants.METHOD_TO_CALL_BOPARM_RIGHT_DEL);
        if (!StringUtils.isBlank(boClassName)
                && boClassName.equals("org.kuali.kfs.module.tem.businessobject.HistoricalTravelExpense")) {
            TravelDocument document = travelForm.getTravelDocument();
            boolean success = true;
            if (document.getTemProfileId() == null) {
                String identifier = TemConstants.documentProfileNames().get(document.getFinancialDocumentTypeCode());

                GlobalVariables.getMessageMap().putError(KNSPropertyConstants.DOCUMENT + "." + KIMPropertyConstants.Person.FIRST_NAME, TemKeyConstants.ERROR_TEM_IMPORT_EXPENSES_PROFILE_MISSING, identifier);
                success = false;

            }
            /*
             * if (document.getPrimaryDestinationId() == null){
             * GlobalVariables.getMessageMap().putError(KNSPropertyConstants.DOCUMENT + "." +
             * TemPropertyConstants.PRIMARY_DESTINATION_NAME ,
             * TemKeyConstants.ERROR_TEM_IMPORT_EXPENSES_PRIMARY_DESTINATION_MISSING); success = false; }
             */
            if (!success) {
                return mapping.findForward(KFSConstants.MAPPING_BASIC);
            }
            travelForm.setRefreshCaller(KFSConstants.MULTIPLE_VALUE);
            GlobalVariables.getUserSession().addObject(TemPropertyConstants.TEMProfileProperties.PROFILE_ID, document.getTemProfileId());
            GlobalVariables.getUserSession().addObject(KFSPropertyConstants.DOCUMENT_TYPE_CODE, (Object) document.getFinancialDocumentTypeCode());
        }
        
        return super.performLookup(mapping, form, request, response);
    }

    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#docHandler(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward docHandler(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final ActionForward retval = super.docHandler(mapping, form, request, response);
        final TravelFormBase travelForm = (TravelFormBase) form;

        initializeNewActualExpenseLines(travelForm.getNewActualExpenseLines(), travelForm.getTravelDocument().getActualExpenses());

        return retval;
    }

    protected void initializeNewActualExpenseLines(List<ActualExpense> newActualExpenseLines, List<ActualExpense> actualExpenses) {
        if (actualExpenses != null && !actualExpenses.isEmpty()) {
            if (newActualExpenseLines == null) {
                newActualExpenseLines = new ArrayList<ActualExpense>();
            }

            newActualExpenseLines.clear();

            for (ActualExpense actualExpense : actualExpenses) {
                ActualExpense newActualExpenseLine = new ActualExpense();
                newActualExpenseLine.setExpenseDate(actualExpense.getExpenseDate());
                newActualExpenseLine.setTravelExpenseTypeCodeId(actualExpense.getTravelExpenseTypeCodeId());
                newActualExpenseLine.setExpenseParentId(actualExpense.getId());

                newActualExpenseLines.add(newActualExpenseLine);
            }
        }
    }

    /**
     * This method will be called when user clicks on Destination not found button on UI under Traveler information section, and
     * enables state code and county fields by setting primary destination indicator to true.
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward enablePrimaryDestinationFields(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase baseForm = (TravelFormBase) form;
        TravelDocumentBase document = (TravelDocumentBase) baseForm.getDocument();

        document.setPrimaryDestinationIndicator(true);
        document.setPrimaryDestinationId(TemConstants.CUSTOM_PRIMARY_DESTINATION_ID);
        // initialize as new primary dest object.
        if (document.getPrimaryDestination() != null) {
            document.getPrimaryDestination().setId(TemConstants.CUSTOM_PRIMARY_DESTINATION_ID);
            document.getPrimaryDestination().setVersionNumber(null);
            document.getPrimaryDestination().setObjectId(null);
        }
        else {
            document.setPrimaryDestination(new PrimaryDestination());
            document.getPrimaryDestination().setId(TemConstants.CUSTOM_PRIMARY_DESTINATION_ID);
            document.getPrimaryDestination().setPrimaryDestinationName(document.getPrimaryDestinationName());
            document.getPrimaryDestination().setCountryState(document.getPrimaryDestinationCountryState());
            document.getPrimaryDestination().setCounty(document.getPrimaryDestinationCounty());
            document.getPrimaryDestination().setTripTypeCode(document.getTripTypeCode());
            document.getPrimaryDestination().setTripType(document.getTripType());
        }
        
        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    protected String getCertificationStatement(final TravelDocument document) {
        if (isEmployee(document.getTraveler())) {
            return getEmployeeCertificationStatement();
        }
        
        return getNonEmployeeCertificationStatement();
    }

    protected String getEmployeeCertificationStatement() {
        return getCertificationStatementFrom(EMPLOYEE_CERTIFICATION_STATEMENT);
    }

    protected String getNonEmployeeCertificationStatement() {
        return getCertificationStatementFrom(NON_EMPLOYEE_CERTIFICATION_STATEMENT);
    }

    protected String getCertificationStatementFrom(final String parameter) {
        return getParameterService().getParameterValue(PARAM_NAMESPACE, DOCUMENT_DTL_TYPE, parameter);
    }

    /**
     * Uses the {@link TravelerService} to determine if the given {@link TravelerDetail} is that of an employee or not
     * 
     * @param traveler is a {@link TravelerDetail}
     * @return true if employee or false otherwise
     * @see @see org.kuali.kfs.module.tem.service.TravelerService#isEmployee(org.kuali.kfs.module.tem.businessobject.TravelerDetail)
     */
    protected boolean isEmployee(final TravelerDetail traveler) {
        if (traveler == null) {
            return false;
        }
        
        return getTravelerService().isEmployee(traveler);
    }

    /**
     * Do initialization for a new {@link TravelDocument}
     * 
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#createDocument(org.kuali.rice.kns.web.struts.form.KualiDocumentFormBase)
     */
    @Override
    protected void createDocument(KualiDocumentFormBase kualiDocumentFormBase) throws WorkflowException {
        super.createDocument(kualiDocumentFormBase);
        final TravelDocument document = (TravelDocument) kualiDocumentFormBase.getDocument();
        document.initiateDocument();

        final List<SpecialCircumstances> specialCircumstances = getTravelDocumentService().findActiveSpecialCircumstances(document.getDocumentHeader().getDocumentNumber(), kualiDocumentFormBase.getDocTypeName());
        document.setSpecialCircumstances(specialCircumstances);
    }

    public <T> T newMvcDelegate(final ActionForm form) throws Exception {
        T retval = (T) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[] { getMvcWrapperInterface() },
                new TravelMvcWrapperInvocationHandler(form));
        return retval;
    }

    /**
     * Forces inheriting classes to define their own MVC Wrappers
     * 
     * @return some {@link Class} instance for an interface that inherits from the {@link TravelMvcWrapperBean}. That's right. It
     *         has to be one of those.
     */
    protected abstract Class<? extends TravelMvcWrapperBean> getMvcWrapperInterface();

    /**
     * Checks if calculation is required. Currently it is required when it has not already been calculated and if the user can
     * perform calculate
     * 
     * @return true if calculation is required, false otherwise
     */
    protected boolean requiresCalculate(TravelFormBase travelForm) {
        boolean requiresCalculate = true;

        requiresCalculate = !travelForm.isCalculated();

        return requiresCalculate;
    }

    /**
     * Uses generics to get whatever the authorizer is for the attached {@link KualiDocumentFormBase}
     * 
     * @return some authorizer connected to the {@link Document} in <code>form</code>
     */
    protected <T> T getDocumentAuthorizer(final KualiDocumentFormBase form) {
        return (T) getDocumentHelperService().getDocumentAuthorizer(form.getDocument());
    }

    protected Integer getLineNumberFromParameter(String parameterKey) {
        int beginIndex = parameterKey.lastIndexOf("[") + 1;
        int endIndex = parameterKey.lastIndexOf("]");
        return Integer.parseInt(parameterKey.substring(beginIndex, endIndex));
    }

    public ActionForward updatePerDiemExpenses(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase reqForm = (TravelFormBase) form;
        TravelDocument document = reqForm.getTravelDocument();
        ParameterService paramService = (ParameterService) SpringContext.getBean(ParameterService.class);

        if (SpringContext.getBean(KualiRuleService.class).applyRules(new UpdateTripDetailsEvent(TRIP_INFO_UPDATE_TRIP_DTL, reqForm.getDocument()))) {
            getTravelDocumentService().updatePerDiemItemsFor(document, document.getPerDiemExpenses(), document.getPrimaryDestinationId(), document.getTripBegin(), document.getTripEnd());
        }

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward clearPerDiemExpenses(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase reqForm = (TravelFormBase) form;
        TravelDocument document = reqForm.getTravelDocument();
        document.setPerDiemExpenses(new ArrayList<PerDiemExpense>());
        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward copyDownPerDiemExpenses(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase reqForm = (TravelFormBase) form;
        TravelDocument document = reqForm.getTravelDocument();

        int copyIndex = getSelectedLine(request);
        getTravelDocumentService().copyDownPerDiemExpense(copyIndex, document.getPerDiemExpenses());

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward addActualExpenseLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final TravelFormBase travelForm = (TravelFormBase) form;
        final TravelMvcWrapperBean mvcWrapper = newMvcDelegate(form);

        travelForm.getObservable().notifyObservers(mvcWrapper);

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward addActualExpenseDetailLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final TravelFormBase travelForm = (TravelFormBase) form;
        final TravelMvcWrapperBean mvcWrapper = newMvcDelegate(form);

        travelForm.getObservable().notifyObservers(new Object[] { mvcWrapper, getSelectedLine(request) });

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward deleteActualExpenseLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final TravelFormBase travelForm = (TravelFormBase) form;
        final TravelMvcWrapperBean mvcWrapper = newMvcDelegate(form);

        travelForm.getObservable().notifyObservers(new Object[] { mvcWrapper, getSelectedLine(request) });

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward deleteActualExpenseDetailLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final TravelFormBase travelForm = (TravelFormBase) form;
        final TravelMvcWrapperBean mvcWrapper = newMvcDelegate(form);
        int[] lineNumbers = getSelectedDetailLine(request);
        travelForm.getObservable().notifyObservers(new Object[] { mvcWrapper, lineNumbers[0], lineNumbers[1] });

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }
    
    protected void displayPDF(HttpServletRequest request, HttpServletResponse response, File reportFile, StringBuilder fileName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String contentDisposition = "";
        try {
            ArrayList master = new ArrayList();
            PdfCopy writer = null;

            // create a reader for the document
            String reportName = reportFile.getAbsolutePath();
            PdfReader reader = new PdfReader(reportName);
            reader.consolidateNamedDestinations();

            // retrieve the total number of pages
            int n = reader.getNumberOfPages();
            List bookmarks = SimpleBookmark.getBookmark(reader);
            if (bookmarks != null)
                master.addAll(bookmarks);

            // step 1: create a document-object
            com.lowagie.text.Document pdfDoc = new com.lowagie.text.Document(reader.getPageSizeWithRotation(1));
            // step 2: create a writer that listens to the document
            writer = new PdfCopy(pdfDoc, baos);
            // step 3: open the document
            pdfDoc.open();
            // step 4: add content
            PdfImportedPage page;
            for (int i = 0; i < n;) {
                ++i;
                page = writer.getImportedPage(reader, i);
                writer.addPage(page);
            }
            writer.freeReader(reader);
            if (!master.isEmpty())
                writer.setOutlines(master);
            // step 5: we close the document
            pdfDoc.close();

            StringBuffer sbContentDispValue = new StringBuffer();
            String useJavascript = request.getParameter("useJavascript");
            if (useJavascript == null || useJavascript.equalsIgnoreCase("false")) {
                sbContentDispValue.append("attachment");
            }
            else {
                sbContentDispValue.append("inline");
            }
            sbContentDispValue.append("; filename=");
            sbContentDispValue.append(fileName);

            contentDisposition = sbContentDispValue.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", contentDisposition);
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentLength(baos.size());

        // write to output
        ServletOutputStream sos;
        sos = response.getOutputStream();
        baos.writeTo(sos);
        sos.flush();
        sos.close();
    }

    public ActionForward customPerDiemExpenses(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase reqForm = (TravelFormBase) form;
        TravelDocument document = reqForm.getTravelDocument();

        int copyIndex = getSelectedLine(request);

        PerDiemExpense expense = document.getPerDiemExpenses().get(copyIndex);

        PerDiem perDiem = new PerDiem();
        perDiem.setId(TemConstants.CUSTOM_PER_DIEM_ID);
        perDiem.setCountryState(document.getPrimaryDestination().getCountryState());
        perDiem.setCounty(document.getPrimaryDestination().getCounty());
        perDiem.setPrimaryDestination(document.getPrimaryDestination().getPrimaryDestinationName());
        perDiem.setTripType(document.getPrimaryDestination().getTripType());
        perDiem.setTripTypeCode(document.getPrimaryDestination().getTripTypeCode());

        // now copy info over to expense
        expense.setPerDiem(perDiem);
        expense.setPerDiemId(perDiem.getId());
        expense.setPrimaryDestination(perDiem.getPrimaryDestination());
        expense.setCountryState(perDiem.getCountryState());
        expense.setCounty(perDiem.getCounty());
        expense.setLodging(perDiem.getLodging());
        expense.setIncidentalsValue(perDiem.getIncidentals());
        expense.setBreakfastValue(new KualiDecimal(perDiem.getBreakfast()));
        expense.setLunchValue(new KualiDecimal(perDiem.getLunch()));
        expense.setDinnerValue(new KualiDecimal(perDiem.getDinner()));

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward refreshAfterPrimaryDestinationLookup(ActionMapping mapping, TravelFormBase reqForm, HttpServletRequest request) {
        String refreshCaller = reqForm.getRefreshCaller();

        TravelDocument document = reqForm.getTravelDocument();

        boolean isPrimaryDestinationLookupable = PRIMARY_DESTINATION_LOOKUPABLE.equals(refreshCaller);

        // if a cancel occurred on address lookup we need to reset the payee id and type, rest of fields will still have correct
        // information
        if (refreshCaller == null) {
            // reqForm.setTravelerId(reqForm.getTempTravelerId());
            return null;
        }

        // do not execute the further refreshing logic if the refresh caller is not a per diem lookupable
        if (!isPrimaryDestinationLookupable) {
            return null;
        }

        Map<String, String> parameters = request.getParameterMap();
        Set<String> parameterKeys = parameters.keySet();
        for (String parameterKey : parameterKeys) {
            if (StringUtils.containsIgnoreCase(parameterKey, TemPropertyConstants.PER_DIEM_EXP)) {
                // its one of the numbered lines, lets
                int estimateLineNum = getLineNumberFromParameter(parameterKey);
                PerDiemExpense expense = document.getPerDiemExpenses().get(estimateLineNum);
                expense.refreshReferenceObject("perDiem");
                PerDiem perDiem = expense.getPerDiem();

                // now copy info over to estimate
                expense.setPerDiem(perDiem);
                expense.setPrimaryDestination(perDiem.getPrimaryDestination());
                expense.setIncidentalsValue(perDiem.getIncidentals());
                expense.setCountryState(perDiem.getCountryState());
                expense.setCounty(perDiem.getCounty());
                expense.setBreakfastValue(new KualiDecimal(perDiem.getBreakfast()));
                expense.setLunchValue(new KualiDecimal(perDiem.getLunch()));
                expense.setDinnerValue(new KualiDecimal(perDiem.getDinner()));
                expense.setLodging(perDiem.getLodging());
                return null;
            }
        }

        Integer primaryDestinationId = document.getPrimaryDestinationId();
        if (primaryDestinationId == null) {
            if (document.getPrimaryDestination().getId() != null) {
                document.setPerDiemExpenses(new ArrayList<PerDiemExpense>());
            }
        }
        else if (document.getPrimaryDestination().getId() != null) {
            if (primaryDestinationId.intValue() != document.getPrimaryDestination().getId()) {
                document.setPerDiemExpenses(new ArrayList<PerDiemExpense>());
            }
        }

        if (isPrimaryDestinationLookupable) {
            PrimaryDestination primaryDestination = new PrimaryDestination();
            primaryDestination.setId(primaryDestinationId);
            document.setPrimaryDestinationIndicator(false);
            primaryDestination = (PrimaryDestination) SpringContext.getBean(BusinessObjectService.class).retrieve(primaryDestination);

            document.setPrimaryDestination(primaryDestination);
            document.setPrimaryDestinationId(primaryDestination.getId());
            document.setTripType(primaryDestination.getTripType());
            document.setTripTypeCode(primaryDestination.getTripTypeCode().toUpperCase());
            return null;
        }

        return null;
    }

    /**
     * Just a passthru {@link InvocationHandler}. It's used when creating a proxy, to access methods in a class without knowing what
     * that class really is. This allows us to put a facade layer on top of whatever MVC we use; hence, the name
     * {@link TravelMvcWrapperInvocationHandler}
     * 
     * @author Leo Przybylski leo [at] rsmart.com
     */
    class TravelMvcWrapperInvocationHandler<MvcClass> implements InvocationHandler {
        private MvcClass mvcObj;

        public TravelMvcWrapperInvocationHandler(final MvcClass mvcObj) {
            this.mvcObj = mvcObj;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Exception {
            return method.invoke(mvcObj, args);
        }
    }

    /**
     * This method calculates the accounting line amount.
     * 
     * @param travelReqForm
     * @return
     */
    KualiDecimal getAccountingLineAmountToFillIn(TravelFormBase travelReqForm) {
        KualiDecimal amount = new KualiDecimal(0);

        TravelDocument travelDocument = travelReqForm.getTravelDocument();
        KualiDecimal encTotal = travelDocument.getEncumbranceTotal();
        KualiDecimal expenseTotal = travelDocument.getExpenseLimit();

        List<SourceAccountingLine> accountingLines = travelDocument.getSourceAccountingLines();

        KualiDecimal accountingTotal = new KualiDecimal(0);
        for (SourceAccountingLine accountingLine : accountingLines) {
            accountingTotal = accountingTotal.add(accountingLine.getAmount());
        }

        if (ObjectUtils.isNull(expenseTotal)) {
            amount = encTotal.subtract(accountingTotal);
        }
        else if (expenseTotal.isLessThan(encTotal)) {
            amount = expenseTotal.subtract(accountingTotal);
        }
        else {
            amount = encTotal.subtract(accountingTotal);
        }

        return amount;
    }

    /**
     * This method calculates trip detail total for both TA and TR
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward recalculateTripDetailTotal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GlobalVariables.getMessageList().remove(new ErrorMessage(TemKeyConstants.MESSAGE_RECALCULATE_SUCCESSFUL));

        // check to make sure all the number fields in the Trip Detail Estimates section are not negative
        TravelFormBase travelReqForm = (TravelFormBase) form;
        TravelDocumentBase travelReqDoc = (TravelDocumentBase) travelReqForm.getDocument();

        // Added to set values to zero when personal box checked to display correct values and totals
        if (SpringContext.getBean(KualiRuleService.class).applyRules(new RecalculateTripDetailTotalEvent("", travelReqForm.getDocument()))) {
            List<PerDiemExpense> estimates = travelReqDoc.getPerDiemExpenses();
            if (estimates != null && !estimates.isEmpty()) {
                getTravelDocumentService().updatePerDiemItemsFor(travelReqDoc, estimates, null, travelReqDoc.getTripBegin(), travelReqDoc.getTripEnd());
            }
        }

        if (!travelReqDoc.isValidExpenses()) {
            return mapping.findForward(KFSConstants.MAPPING_BASIC);
        }

        travelReqForm.getNewSourceLine().setAmount(this.getAccountingLineAmountToFillIn(travelReqForm));

        // forcing a refresh does the same as recalculation (unless we change how we do it)
        debug("Recalculating travel auth document ", travelReqDoc.getTravelDocumentIdentifier());
        travelReqForm.setCalculated(true);

        GlobalVariables.getMessageList().add(TemKeyConstants.MESSAGE_RECALCULATE_SUCCESSFUL);
        
        showAccountDistribution(request, travelReqForm.getDocument());
        
        if (travelReqForm.getDocument() instanceof TravelReimbursementDocument) {
            final boolean showAdvances = getParameterService().getIndicatorParameter(PARAM_NAMESPACE, TravelReimbursementParameters.PARAM_DTL_TYPE, DISPLAY_ADVANCES_IN_REIMBURSEMENT_TOTAL_IND);
            request.setAttribute(SHOW_ADVANCES_ATTRIBUTE, showAdvances);
        }
                
        // Flag to display reports (TR, ENT, RELO)
        request.setAttribute(SHOW_REPORTS_ATTRIBUTE, !travelReqDoc.getDocumentHeader().getWorkflowDocument().stateIsInitiated());
        
        if (travelReqDoc.getReimbursableTotal() != null && travelReqDoc.getExpenseLimit() != null && travelReqDoc.getReimbursableTotal().isGreaterThan(travelReqDoc.getExpenseLimit())) {
            GlobalVariables.getMessageMap().putWarning(KFSConstants.DOCUMENT_PROPERTY_NAME + "." + TemPropertyConstants.TRVL_AUTH_TOTAL_ESTIMATE, KFSKeyConstants.ERROR_CUSTOM, "Travel expense is exceeding the expense limit.");
        }        
        
        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    protected void showAccountDistribution(HttpServletRequest request, Document document) {
        String paramDetailType = null;
        if (document instanceof TravelReimbursementDocument) {
            paramDetailType = TravelReimbursementParameters.PARAM_DTL_TYPE;
        } else if (document instanceof TravelEntertainmentDocument) {
            paramDetailType = TravelEntertainmentParameters.PARAM_DTL_TYPE;
        } else if (document instanceof TravelRelocationDocument) {
            paramDetailType = TravelRelocationParameters.PARAM_DTL_TYPE;
        } else if (document instanceof TravelAuthorizationDocument) {
            paramDetailType = TravelAuthorizationParameters.PARAM_DTL_TYPE;
        }
        
        if (paramDetailType != null) {
            final boolean showAccountDistribution = getParameterService().getIndicatorParameter(PARAM_NAMESPACE, paramDetailType, ENABLE_ACCOUNTING_DISTRIBUTION_TAB_IND);
            request.setAttribute(SHOW_ACCOUNT_DISTRIBUTION_ATTRIBUTE, showAccountDistribution);
        }
    }

    /**
     * This method wraps recalculateTripDetailTotal and removes the success message
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward recalculateTripDetailTotalOnly(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // call recalculateTripDetailTotal where all the magic happens
        ActionForward actionForward = recalculateTripDetailTotal(mapping, form, request, response);
        GlobalVariables.getMessageList().remove(new ErrorMessage(TemKeyConstants.MESSAGE_RECALCULATE_SUCCESSFUL));

        return actionForward;
    }

    /**
     * This method determines if the user is a travel arranger
     * 
     * @return true if they are a travel arranger
     */
    protected boolean setTravelArranger(TravelFormBase reqForm) {
        // Find if they have the role
        String homeDepartment = null;
        TravelDocument travelDocument = reqForm.getTravelDocument();

        Map<String, String> primaryKeys = new HashMap<String, String>();
        if (ObjectUtils.isNotNull(travelDocument.getTemProfileId())) {
            primaryKeys.put(TEMProfileProperties.PROFILE_ID, travelDocument.getTemProfileId().toString());
            TEMProfile profile = (TEMProfile) SpringContext.getBean(BusinessObjectService.class).findByPrimaryKey(TEMProfile.class, primaryKeys);

            if (ObjectUtils.isNotNull(profile)) {
                homeDepartment = profile.getHomeDepartment();
            }
        }

        return getTravelDocumentService().isTravelArranger(GlobalVariables.getUserSession().getPerson(), homeDepartment);
    }

    /**
     * This method determines if the user is a travel manager
     * 
     * @return true if they are a travel manager
     */
    protected boolean setTravelManager(TravelFormBase reqForm) {
        // Find if they have the role
        return getTravelDocumentService().isTravelManager(GlobalVariables.getUserSession().getPerson());
    }

    /**
     * This method determines if the user is a fiscal officer
     * 
     * @return true if they are a fiscal officer
     */
    protected boolean setFiscalOfficer(TravelFormBase reqForm) {
        // Find if they have the role

        // checking this is causing a RuntimeException when the workflow document is null (when generating a report).
        // apparently even checking if a workflow document is null will cause it to send a RuntimeException - hence the try catch.
        boolean workflowCheck = false;
        try {
            workflowCheck = reqForm.getWorkflowDocument().getCurrentRouteNodeNames().equals(TemWorkflowConstants.RouteNodeNames.ACCOUNT) && reqForm.getWorkflowDocument().stateIsEnroute();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }

        return getTravelDocumentService().isResponsibleForAccountsOn(reqForm.getTravelDocument(), GlobalVariables.getUserSession().getPerson().getPrincipalId()) && workflowCheck;
    }

    protected List<String> getCalculateIgnoreList() {
        return Arrays.asList(methodToCallExclusionArray);
    }

    /**
     * This method adds a new related document into the travel request document
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addRelatedDocumentLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase travelReqForm = (TravelFormBase) form;

        AccountingDocumentRelationship adr = travelReqForm.getNewAccountingDocumentRelationship();
        if (adr.getRelDocumentNumber() == null || !NumberUtils.isDigits(adr.getRelDocumentNumber())) {
            GlobalVariables.getMessageMap().putError(
                    String.format("%s.%s",
                            TemKeyConstants.TRVL_RELATED_DOCUMENT,
                            TemPropertyConstants.TRVL_RELATED_DOCUMENT_NUM),
                    TemKeyConstants.ERROR_TRVL_RELATED_DOCUMENT_REQUIRED);
        }
        else {
            if (getDocumentService().documentExists(adr.getRelDocumentNumber())) {
                adr.setDocumentNumber(travelReqForm.getDocument().getDocumentNumber());
                adr.setPrincipalId(GlobalVariables.getUserSession().getPerson().getPrincipalId());
                getAccountingDocumentRelationshipService().save(adr);
                travelReqForm.setNewAccountingDocumentRelationship(new AccountingDocumentRelationship());
            }
            else {
                GlobalVariables.getMessageMap().putError(
                        String.format("%s.%s",
                                TemKeyConstants.TRVL_RELATED_DOCUMENT,
                                TemPropertyConstants.TRVL_RELATED_DOCUMENT_NUM),
                        TemKeyConstants.ERROR_TRVL_RELATED_DOCUMENT_NOT_FOUND, new String[] { adr.getRelDocumentNumber() });
            }
        }
        
        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward deleteRelatedDocumentLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase travelReqForm = (TravelFormBase) form;
        String parameterName = (String) request.getAttribute(KNSConstants.METHOD_TO_CALL_ATTRIBUTE);
        if (StringUtils.isNotBlank(parameterName)) {
            String relDocumentNumber = StringUtils.substringBetween(parameterName, "deleteRelatedDocumentLine.", ".");
            List<AccountingDocumentRelationship> adrList = getAccountingDocumentRelationshipService().find(new AccountingDocumentRelationship(travelReqForm.getDocument().getDocumentNumber(), relDocumentNumber));

            if (adrList != null && adrList.size() == 1) {
                if (adrList.get(0).getPrincipalId().equals(GlobalVariables.getUserSession().getPerson().getPrincipalId())) {
                    getAccountingDocumentRelationshipService().delete(adrList.get(0));
                }
            }
        }
        
        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    /**
     * This method adds a new group traveler into the travel document
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addGroupTravelerLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase travelReqForm = (TravelFormBase) form;
        TravelDocumentBase travelReqDoc = (TravelDocumentBase) travelReqForm.getDocument();

        GroupTraveler newGroupTravelerLine = travelReqForm.getNewGroupTravelerLine();
        boolean rulePassed = true;
        // check any business rules
        rulePassed &= SpringContext.getBean(KualiRuleService.class).applyRules(new AddGroupTravelLineEvent("newGroupTravelerLine", travelReqForm.getDocument(), newGroupTravelerLine));

        if (rulePassed) {
            travelReqDoc.addGroupTravelerLine(newGroupTravelerLine);
            travelReqForm.setNewGroupTravelerLine(new GroupTraveler());
        }

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }


    /**
     * This method removes a group traveler from this collection
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteGroupTravelerLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase travelReqForm = (TravelFormBase) form;
        TravelDocumentBase travelReqDoc = (TravelDocumentBase) travelReqForm.getDocument();

        int deleteIndex = getLineToDelete(request);
        travelReqDoc.getGroupTravelers().remove(deleteIndex);

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    /**
     * Import group travel to the document from a spreadsheet.
     * 
     * @param mapping An ActionMapping
     * @param form An ActionForm
     * @param request The HttpServletRequest
     * @param response The HttpServletResponse
     * @throws Exception
     * @return An ActionForward
     */
    public ActionForward uploadGroupTravelerImportFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase reqForm = (TravelFormBase) form;
        TravelDocumentBase travelDoc = (TravelDocumentBase) reqForm.getDocument();

        List<Object> importedGroupTraveler = null;
        String tabErrorKey = "groupTraveler";
        try {
            importedGroupTraveler = UploadParser.importFile(reqForm.getGroupTravelerImportFile(), GroupTraveler.class, GROUP_TRAVELER_ATTRIBUTE_NAMES, null, null, tabErrorKey);
            // importedGroupTraveler = UploadParser.importFile(reqForm.getGroupTravelerImportFile(), GroupTraveler.class, GROUP_TRAVELER_ATTRIBUTE_NAMES, tabErrorKey);
            
            // validate imported items
            boolean allPassed = true;
            int itemLineNumber = 0;
            for (Object o : importedGroupTraveler) {
                allPassed &= SpringContext.getBean(KualiRuleService.class).applyRules(new AddGroupTravelLineEvent("newGroupTravelerLine", travelDoc, (GroupTraveler) o));
            }
            if (allPassed) {
                for (Object o : importedGroupTraveler) {
                    GroupTraveler groupTraveler = (GroupTraveler) o;
                    groupTraveler.setDocumentNumber(travelDoc.getDocumentNumber());
                    groupTraveler.setFinancialDocumentLineNumber(travelDoc.getGroupTravelers().size() + 1);
                    travelDoc.getGroupTravelers().add(groupTraveler);
                }
            }
        }
        catch (UploadParserException e) {
            GlobalVariables.getMessageMap().putError(tabErrorKey, e.getErrorKey(), e.getErrorParameters());
        }

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward payDVToVendor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward createREQSForVendor(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward addImportedExpenseLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final TravelFormBase travelForm = (TravelFormBase) form;
        final TravelMvcWrapperBean mvcWrapper = newMvcDelegate(form);

        travelForm.getObservable().notifyObservers(mvcWrapper);

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward addImportedExpenseDetailLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final TravelFormBase travelForm = (TravelFormBase) form;
        final TravelMvcWrapperBean mvcWrapper = newMvcDelegate(form);

        travelForm.getObservable().notifyObservers(new Object[] { mvcWrapper, getSelectedLine(request) });

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward deleteImportedExpenseLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final TravelFormBase travelForm = (TravelFormBase) form;
        final TravelMvcWrapperBean mvcWrapper = newMvcDelegate(form);

        travelForm.getObservable().notifyObservers(new Object[] { mvcWrapper, getSelectedLine(request) });
        this.save(mapping, travelForm, request, response);
        GlobalVariables.getMessageList().add(TemKeyConstants.INFO_TEM_IMPORT_DOCUMENT_SAVE);
        
        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward deleteImportedExpenseDetailLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final TravelFormBase travelForm = (TravelFormBase) form;
        final TravelMvcWrapperBean mvcWrapper = newMvcDelegate(form);
        int[] lineNumbers = getSelectedDetailLine(request);
        travelForm.getObservable().notifyObservers(new Object[] { mvcWrapper, lineNumbers[0], lineNumbers[1] });

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    protected int[] getSelectedDetailLine(HttpServletRequest request) {
        int[] selectedLines = new int[2];
        String parameterName = (String) request.getAttribute(KNSConstants.METHOD_TO_CALL_ATTRIBUTE);
        if (StringUtils.isNotBlank(parameterName)) {
            String lineNumbers = StringUtils.substringBetween(parameterName, ".line", ".");
            selectedLines[0] = Integer.parseInt(lineNumbers.split("-")[0]);
            selectedLines[1] = Integer.parseInt(lineNumbers.split("-")[1]);
        }

        return selectedLines;
    }

    /**
     * @see org.kuali.kfs.sys.web.struts.KualiAccountingDocumentActionBase#deleteSourceLine(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward deleteSourceLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.deleteSourceLine(mapping, form, request, response);
        TravelFormBase travelForm = (TravelFormBase) form;
        travelForm.setAnchor(TemConstants.SOURCE_ANCHOR);
        travelForm.setDistribution(new ArrayList<AccountingDistribution>());
        travelForm.setDistribution(getAccountingDistributionService().buildDistributionFrom(travelForm.getTravelDocument()));
        
        return forward;
    }

    public ActionForward deleteDistributionLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase travelForm = (TravelFormBase) form;
        travelForm.setAnchor(TemConstants.DISTRIBUTION_ANCHOR);
        final TravelMvcWrapperBean mvcWrapper = newMvcDelegate(form);
        int deleteIndex = getLineToDelete(request);
        travelForm.getObservable().notifyObservers(new Object[] { mvcWrapper, deleteIndex });

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward insertDistributionLine(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase travelForm = (TravelFormBase) form;
        travelForm.setAnchor(TemConstants.DISTRIBUTION_ANCHOR);
        final TravelMvcWrapperBean mvcWrapper = newMvcDelegate(form);

        travelForm.getObservable().notifyObservers(mvcWrapper);

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    /**
     * @see org.kuali.kfs.sys.web.struts.KualiAccountingDocumentActionBase#getSourceAccountingLine(org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected SourceAccountingLine getSourceAccountingLine(ActionForm form, HttpServletRequest request) {
        // TODO Auto-generated method stub
        return super.getSourceAccountingLine(form, request);
    }

    /**
     * @see org.kuali.kfs.sys.web.struts.KualiAccountingDocumentActionBase#deleteAccountingLine(boolean,
     *      org.kuali.kfs.sys.web.struts.KualiAccountingDocumentFormBase, int)
     */
    @Override
    protected void deleteAccountingLine(boolean isSource, KualiAccountingDocumentFormBase financialDocumentForm, int deleteIndex) {
        // TODO Auto-generated method stub
        financialDocumentForm.setAnchor(TemConstants.SOURCE_ANCHOR);
        super.deleteAccountingLine(isSource, financialDocumentForm, deleteIndex);
    }

    /**
     * @see org.kuali.kfs.sys.web.struts.KualiAccountingDocumentActionBase#insertAccountingLine(boolean,
     *      org.kuali.kfs.sys.web.struts.KualiAccountingDocumentFormBase, org.kuali.kfs.sys.businessobject.AccountingLine)
     */
    @Override
    protected void insertAccountingLine(boolean isSource, KualiAccountingDocumentFormBase financialDocumentForm, AccountingLine line) {
        // TODO Auto-generated method stub
        financialDocumentForm.setAnchor(TemConstants.SOURCE_ANCHOR);
        super.insertAccountingLine(isSource, financialDocumentForm, line);
    }

    public ActionForward selectAllDistributions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("anchoraccountingDistributionAnchor", "anchoraccountingDistributionAnchor");
        TravelFormBase travelForm = (TravelFormBase) form;
        int index = getSelectedLine(request);
        boolean selected = false;
        if (index == Integer.parseInt(TemConstants.SELECT_ALL_INDEX)) {
            selected = true;
        }
        for (AccountingDistribution accountingDistribution : travelForm.getDistribution()) {
            accountingDistribution.setSelected(selected);
        }

        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    /**
     * Action method that handles setting up the distribution of expenses. Expenses are distributed by object code across
     * {@link AccountingLine} instances. When a distribution is setup, the relevant information is added to the new
     * {@link AccountingLine} on the {@link TravelReimbursementForm}
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return {@link ActionForward}
     */
    public ActionForward distribute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase travelForm = (TravelFormBase) form;
        travelForm.setAnchor(TemConstants.SUMMARY_ANCHOR);
        TemDistributionAccountingLine newLine = getAccountingDistributionService().distributionToDistributionAccountingLine(travelForm.getDistribution());
        travelForm.setAccountDistributionsourceAccountingLines(new ArrayList<TemDistributionAccountingLine>());
        travelForm.setAccountDistributionnextSourceLineNumber(new Integer(1));
        newLine.setAccountLinePercent(new BigDecimal(100));
        travelForm.setAccountDistributionnewSourceLine(newLine);
        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward distributeAccountingLines(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final TravelFormBase travelForm = (TravelFormBase) form;
        travelForm.setAnchor(TemConstants.DISTRIBUTION_ANCHOR);
        final TravelMvcWrapperBean mvcWrapper = newMvcDelegate(form);

        travelForm.getObservable().notifyObservers(mvcWrapper);
        
        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    public ActionForward resetAccountingLines(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TravelFormBase travelForm = (TravelFormBase) form;
        TravelDocument document = travelForm.getTravelDocument();
        document.setNextSourceLineNumber(new Integer(1));
        document.setSourceAccountingLines(new ArrayList<TemSourceAccountingLine>());
        travelForm.setDistribution(new ArrayList<AccountingDistribution>());
        travelForm.setDistribution(getAccountingDistributionService().buildDistributionFrom(document));
        travelForm.setAnchor(TemConstants.SOURCE_ANCHOR);
        
        return mapping.findForward(KFSConstants.MAPPING_BASIC);
    }

    /**
     * @see org.kuali.kfs.sys.web.struts.KualiAccountingDocumentActionBase#approve(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        recalculateTripDetailTotalOnly(mapping, form, request, response);
        
        ActionForward forward = super.approve(mapping, form, request, response);

        TravelFormBase travelForm = (TravelFormBase) form;
        TravelDocument document = travelForm.getTravelDocument();

        if (getTravelDocumentService().checkHoldGLPEs(document)) {
            TravelDocument travelDoc = travelForm.getTravelDocument();
            for (GeneralLedgerPendingEntry entry : travelDoc.getGeneralLedgerPendingEntries()) {
                entry.setUniversityFiscalYear(null);
                entry.setUniversityFiscalPeriodCode(null);
                entry.setFinancialDocumentApprovedCode(KFSConstants.PENDING_ENTRY_APPROVED_STATUS_CODE.HOLD);
            }
            getBusinessObjectService().save(travelDoc.getGeneralLedgerPendingEntries());
        }

        return forward;
    }

    /**
     * is this document in a final workflow state
     * 
     * @param reqForm
     * @return
     */
    protected boolean isFinal(TravelFormBase reqForm) {
        return reqForm.getTransactionalDocument().getDocumentHeader().getWorkflowDocument().stateIsFinal();
    }

    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#disapprove(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward disapprove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Document is disapproved. Free up imported expenses
        TravelFormBase travelForm = (TravelFormBase) form;
        TravelDocument document = travelForm.getTravelDocument();
        getTravelDocumentService().detachImportedExpenses(document);
        
        return super.disapprove(mapping, form, request, response);
    }

    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#cancel(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.cancel(mapping, form, request, response);

        if (forward.getName() != null && forward.getName().equals(KNSConstants.MAPPING_PORTAL)) {
            // Document is cancelled. Free up imported expenses
            TravelFormBase travelForm = (TravelFormBase) form;
            TravelDocument document = travelForm.getTravelDocument();

            getTravelDocumentService().detachImportedExpenses(document);
        }
        
        return forward;
    }

    /**
     * @see org.kuali.rice.kns.web.struts.action.KualiDocumentActionBase#close(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward close(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ActionForward forward = super.close(mapping, form, request, response);
        TravelFormBase travelForm = (TravelFormBase) form;
        TravelDocument document = travelForm.getTravelDocument();

        if (forward.getName() != null && forward.getName().equals(KNSConstants.MAPPING_PORTAL)) {
            // Document is not saved. Free up imported expenses.
            if (document.getDocumentHeader().getWorkflowDocument().stateIsInitiated()) {
                getTravelDocumentService().detachImportedExpenses(document);
            }
        }

        return forward;
    }

    /**
     * @see org.kuali.kfs.sys.web.struts.KualiAccountingDocumentActionBase#save(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        recalculateTripDetailTotalOnly(mapping, form, request, response);
        
        TravelFormBase travelForm = (TravelFormBase) form;
        TravelDocument document = travelForm.getTravelDocument();
        ActionForward forward = super.save(mapping, form, request, response);
        getTravelDocumentService().attachImportedExpenses(document);
        
        return forward;
    }

    /**
     * @see org.kuali.kfs.sys.web.struts.KualiAccountingDocumentActionBase#blanketApprove(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward blanketApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        recalculateTripDetailTotalOnly(mapping, form, request, response);
        
        TravelFormBase travelForm = (TravelFormBase) form;
        TravelDocument document = travelForm.getTravelDocument();
        ActionForward forward = super.blanketApprove(mapping, form, request, response);
        getTravelDocumentService().attachImportedExpenses(document);
        
        return forward;
    }

    /**
     * is this document in a processed workflow state?
     * 
     * @param reqForm
     * @return
     */
    protected boolean isProcessed(TravelFormBase reqForm) {
        return reqForm.getTransactionalDocument().getDocumentHeader().getWorkflowDocument().stateIsProcessed();
    }
    
    protected void refreshRelatedDocuments(TravelFormBase form) {
        Map<String, List<Document>> relatedDocuments;
        try {
            relatedDocuments = getTravelDocumentService().getDocumentsRelatedTo(form.getTravelDocument());
            form.setRelatedDocuments(relatedDocuments);
            form.setRelatedDocumentNotes(null);
            form.getRelatedDocumentNotes();
        }
        catch (WorkflowException ex) {
            ex.printStackTrace();
        }
    }
}
