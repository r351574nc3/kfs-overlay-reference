/*
 * Copyright 2008 The Kuali Foundation
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
package org.kuali.kfs.module.tem;

import org.kuali.rice.core.util.JSTLConstants;

/**
 * Travel and Entertainment module constants for struts error keys
 * 
 * @author Leo Przybylski (leo [at] rsmart.com) 
 */
public class TemKeyConstants extends JSTLConstants {
    public static final String TRMB_CONTACT_TAB_ERRORS = "TRMBContactErrors,document.contact*";
    public static final String TRMB_EMERGENCY_CONTACT_TAB_ERRORS = "TRMBEmergencyContactErrors,document.regionFamiliarity,document.emergencyContact*";    
    public static final String TEM_TRAVELER_CERT_TAB_ERRORS = "TRMBTravelerCertErrors,document.employeeCertification";
    
    public static final String TA_QUESTION_DOCUMENT                 = "travelAuthorization.question.text";
    public static final String TA_MESSAGE_HOLD_DOCUMENT             = "travelAuthorization.route.message.hold.text";
    public static final String TA_MESSAGE_REMOVE_HOLD_DOCUMENT      = "travelAuthorization.route.message.removehold.text";
    public static final String ERROR_TA_REASON_REQUIRED             = "error.travelAuthorization.reasonRequired";
    public static final String ERROR_TA_REASON_PASTLIMIT            = "error.travelAuthorization.reason.pastLimit";
    public static final String TR_FISCAL_OFFICER_QUESTION           = "travelAuthorization.fiscalOfficer.question.text";
    public static final String TA_MESSAGE_HOLD_DOCUMENT_TEXT        = "travelAuthorization.message.hold.text";
    public static final String TA_MESSAGE_AMEND_DOCUMENT_TEXT       = "travelAuthorization.message.amend.text";
    public static final String TA_MESSAGE_RETIRED_DOCUMENT_TEXT     = "travelAuthorization.message.retired.text";
    public static final String TA_MESSAGE_CLOSE_DOCUMENT_TEXT       = "travelAuthorization.message.close.text";
    
    public static final String AGENCY_SITES_URL       = "url.document.travelRelocation.agencySites";
    public static final String ENABLE_AGENCY_SITES_URL  = "config.document.travelRelocation.agencySites.enable";
    public static final String PASS_TRIP_ID_TO_AGENCY_SITES  = "config.document.travelRelocation.agencySites.include.tripId";

    public static final String TEM_PROFILE_ARRANGERS_QUESTION       = "tem.profile.arrangers.question.text";
    public static final String GENERATE_TEM_PROFILE_ID_QUESTION_ID  = "GenerateTEMProfileQuestionID";
    public static final String TA_MESSAGE_AMEND_DOCUMENT_CANCELLED_TEXT = "travelAuthorization.message.amend.cancelled.text";
    
    // errors
    public static final String ERROR_TA_AUTH_EMERGENCY_CONTACT_REQUIRED   = "error.document.tem.emergencyContactRequired";
    public static final String ERROR_TA_AUTH_MODE_OF_TRANSPORT_REQUIRED   = "error.document.tem.modeOfTransportRequired";
    public static final String ERROR_TA_AUTH_END_DATE_BEFORE_BEGIN        = "error.document.tem.endDateBeforeBegin";
    public static final String ERROR_ACTUAL_EXPENSE_OTHER_MILEAGE_RATE_EXCEED = "error.document.tem.actualexpense.mileagerateexceed";
    public static final String ERROR_ACTUAL_EXPENSE_DETAIL_AMOUNT_EXCEED  = "error.document.tem.actualexpense.totaldetailamountexceed";
    public static final String ERROR_ACTUAL_EXPENSE_LODGING_ENTERED       = "error.document.tem.actualexpense.lodgingentered";
    public static final String ERROR_ACTUAL_EXPENSE_DUPLICATE_ENTRY       = "error.document.tem.actualexpense.duplicateexpenseentry";
    public static final String ERROR_ACTUAL_EXPENSE_DUPLICATE_ENTRY_DAILY = "error.document.tem.actualexpense.duplicateexpenseentrydaily";
    public static final String ERROR_ACTUAL_EXPENSE_MAX_AMT_PER_DAILY     = "error.document.tem.actualexpense.maxamountperpersondaily";
    public static final String ERROR_ACTUAL_EXPENSE_MAX_AMT_PER_OCCU      = "error.document.tem.actualexpense.maxamountperoccurrence";
    public static final String ERROR_DELINQUENT_MSG_REQUIRED              = "error.document.tem.delinquentMessageRequired";
    
    public static final String WARNING_NOTES_JUSTIFICATION             = "warning.document.tem.actualexpense.notesJustification";
    public static final String WARNING_PERDIEM_EXPENSE_LODGING_ENTERED = "warning.document.tem.perdiemexpense.lodgingentered";

    // errors
    public static final String TRVL_AUTH_TRVL_ADVANCE_ERRORS = "newTravelAdvanceLine.*,document.travelAdvances*";

    public static final String TRVL_AUTH_TRIP_OVERVIEW_ERRORS = "tripInfoUpdateTripDetails.*,document.tripBegin,document.tripEnd,document.tripTypeCode,document.primaryDestinationName," +
                                                                "document.travelerTypeCode,document.firstName,document.lastName,document.streetAddressLine1,"+
                                                                "document.stateCode,document.zipCode,document.phoneNumber,document.tripDescription,document.delinquentTRMessage,tripOverview.*";
    public static final String TRVL_ENT_ERRORS ="document.tripBegin,document.tripEnd,document.tripTypeCode," +
                                                                "document.travelerTypeCode,document.firstName,document.lastName,document.streetAddressLine1,"+
                                                                "document.stateCode,document.purposeCode,document.paymentMethod,document.hostName,document.zipCode,document.phoneNumber,tripOverview.*,document.traveler*";
    public static final String TRVL_AUTH_EMERGENCY_CONTACT_ERRORS = "newEmergencyContactLine.*,emergencyContact.*,document.regionFamiliarity,document.cellPhoneNumber,document.traveler.emergencyContacts*";
    public static final String TRVL_AUTH_SPECIAL_CIRCUMSTANCES_ERRORS = "document.expenseLimit,document.mealWithoutLodgingReason";
    public static final String TRVL_EXPENSES = "newActualExpenseLine.*,newActualExpenseLine*,newImportedExpenseLines*," +
    		                                    "document.importedExpenses*,document.actualExpenses*";
    public static final String TRVL_PER_DIEM_EXPENSES = "document.perDiemExpenses*,perDiemExpenses*";
    public static final String TRVL_AUTH_TRVL_EXPENSES_TOTAL_ERRORS = "document.estimateAmount";
    public static final String TRVL_REIMB_AIRFARE_ERROR = "document.airfareNote,document.airfare,newActualExpenseLine.*";
    public static final String TRVL_REIMB_CONTACT_INFO_ERRORS = "document.contactName,document.contactPhoneNum";
    public static final String TRVL_REIMB_ACCOUNTING_DISTRIBUTION_ERRORS = "accountingDistribution";    
    public static final String TRVL_ENT_ATTENDEE_ERRORS = "document.numberOfAttendees,document.attendeeListAttached,newAttendeeLines*,attendee,attendeeType,company,name,title";
    public static final String TRVL_GROUP_TRVL_ERRORS = "newGroupTravelerLine.name,newGroupTravelerLine.travelerTypeCode,newGroupTravelerLine.groupTravelerEmpId,groupTraveler";
    public static final String ERROR_TR_EMPLOYEE_INITIATOR_MUST_CERTIFY ="error.document.tem.employeeCertification";
    public static final String TRVL_ACCOUNT_DIST = "accountDistributionsourceAccountingLines*,accountDistributionnewSourceLine*";
    
    
    //travel arranger errors
    public static final String TRVL_ARRGR_TRAVELER_ERRORS = "document.profileId,document.travelerName,travelerName";
    public static final String TRVL_ARRGR_REQUEST_ERRORS = "document.resign,document.taInd,document.trInd,document.primaryInd,resign,primaryInd";
    public static final String ERROR_TTA_ARRGR_TRVLR_SAME= "error.tta.arrangerTravelerSame";
    public static final String ERROR_TTA_ARRGR_ONE_PRIMARY = "error.tta.arrangerOnePrimary";
    public static final String ERROR_TTA_ARRGR_RESIGN = "error.tta.resignNoOtherFields";
    
    public static final String ERROR_TA_EM_CONTACT_NAME = "error.ta.emergencyContactNameIsBlank";
    public static final String ERROR_TA_EM_CONTACT_PHONE = "error.ta.emergencyContactPhoneNumIsBlank";
    public static final String ERROR_TA_EM_REL_TYPE = "error.ta.emergencyContactRelationTypeIsBlank";
    public static final String ERROR_TA_TRVL_REQ_GRTR_THAN_ZERO = "error.document.tem.travelAdvRequestedGreaterThanZero";
    public static final String ERROR_TA_TRVL_ADV_MISSING_PROFILE = "error.document.tem.travel.advance.profile.missing";
    public static final String ERROR_TA_TRVL_TRIP_END_MISSING = "error.document.tem.travel.trip.end.date.missing";
    public static final String ERROR_TA_TRVL_ADV_DUE_DATE_INVALID = "error.document.tem.travel.advance.due.date.invalid";
    public static final String ERROR_TA_TRVL_ADV_POLICY = "error.document.tem.travel.advance.policy";
    public static final String ERROR_TA_TRVL_ADV_ADD_JUST = "error.document.tem.travel.advance.additional.justification";
    public static final String ERROR_TA_TRVL_ADV_DUE_DATE_MISSING = "error.document.tem.travel.advance.due.date.missing";
    public static final String ERROR_TA_TRIP_BEGIN_EMPTY = "error.document.tem.tripBeginEmpty";
    public static final String ERROR_TA_TRIP_END_EMPTY= "error.document.tem.tripEndEmpty";
    public static final String ERROR_TA_TRIP_TYPE_CD_EMPTY = "error.document.tem.tripTypeCodeEmpty";
    public static final String ERROR_TRIP_TYPE_CD_PRI_DEST_MISMATCH = "error.document.tem.trip.type.primary.destination.mismatch";
    public static final String ERROR_TA_AR_CUST_NOT_FOUND = "error.document.tem.arCustomerNotFound";
    public static final String ERROR_TA_PRINCPL_ID_REQ_FOR_EMPLOYEE = "error.document.tem.principalIdRequiredForEmployee";
    public static final String ERROR_TA_BLANKET_TYPE_NO_ESTIMATE = "error.document.tem.blanketTypeNoEstimate";
    public static final String ERROR_TA_BLANKET_TYPE_NO_EXPENSES = "error.document.tem.blanketTypeNoExpenses";
    public static final String ERROR_TA_NO_MILEAGE_RATE = "error.document.tem.noMileageRate";
    public static final String ERROR_TA_NO_NEGATIVE_AMOUNT = "error.document.tem.noMileageRate";
    public static final String ERROR_TA_MEAL_AND_INC_NOT_VALID = "error.document.tem.invalidMealsAndIncidentals";
    public static final String ERROR_TA_ENCUMBRANCE_OBJ_CD_INVALID = "error.document.tem.invalid.encumbrance.object.code";
    public static final String ERROR_TRAVELER_TYPES_NOT_CONFIGURED = "error.document.tem.travelerSearch";
    public static final String ERROR_TRAVELER_TYPES_NOT_SELECTED = "error.document.tem.travelerSearchEmployeeType";
    public static final String ERROR_PHONE_NUMBER = "error.document.tem.phoneNumber";
    public static final String ERROR_EMERGENCY_PHONE_NUMBER = "error.document.tem.contact.phoneNumber";
    public static final String ERROR_REQUIRES_CALCULATE = "error.document.calculationRequired";
    public static final String MESSAGE_RECALCULATE_SUCCESSFUL = "message.recalculate.successful";
    public static final String MESSAGE_HOSTED_MEAL_ADDED = "message.hosted.meal";
    public static final String MESSAGE_TR_MEAL_ALREADY_CLAIMED = "message.document.tem.mealAlreadyClaimed";
    public static final String MESSAGE_TR_AIRFARE_ALREADY_CLAIMED = "message.document.tem.airfareAlreadyClaimed";
    public static final String MESSAGE_TR_LODGING_ALREADY_CLAIMED = "message.document.tem.lodgingAlreadyClaimed";
    public static final String MESSAGE_DV_IN_ACTION_LIST       = "message.document.tem.dv.actionList";
    public static final String MESSAGE_RELO_DV_IN_ACTION_LIST = "message.document.tem.relo.dv.actionList";
    
    //Add any custom messages
    public static final String MESSAGE_GENERIC = "message.generic";
    
    public static final String ERROR_TEM_PROFILE_CHART_NOT_EXIST = "error.tem.profile.chartMustExist";
    public static final String ERROR_TEM_PROFILE_ACCOUNT_NOT_EXIST = "error.tem.profile.accountNumberMustExist";
    public static final String ERROR_TEM_PROFILE_SUB_ACCOUNT_NOT_EXIST = "error.tem.profile.subAccountNumberMustExist";
    public static final String ERROR_TEM_PROFILE_PROJECT_CODE_NOT_EXIST = "error.tem.profile.projectCodeMustExist";
    
    public static final String ERROR_TEM_PROFILE_ACCOUNT_EFFECTIVE_DATE = "error.tem.profile.account.effectiveDate";
    public static final String ERROR_TEM_PROFILE_ACCOUNT_EXPIRATION_DATE = "error.tem.profile.account.expirationDate";
    
    public static final String ERROR_TEM_PROFILE_ARRANGER_PRIMARY ="error.tem.profile.arranger.primary";
    public static final String ERROR_TEM_PROFILE_ARRANGER_DUPLICATE ="error.tem.profile.arranger.duplicate";
    public static final String ERROR_TEM_PROFILE_ARRANGER_NONEMPLOYEE ="error.tem.profile.arranger.nonemployee";

    public static final String ERROR_TA_FISCAL_OFFICER_ACCOUNT = "error.document.fiscalofficer.account";
    public static final String ERROR_EXPENSE_DATE_BEFORE_AFTER = "error.document.tem.expenseDateBeforeAfter";
    
    public static final String ERROR_AUTHORIZATION_TA_INITIATION = "error.authorization.travelAuthorizationInitiation";
    public static final String ERROR_AUTHORIZATION_TA_EDIT = "error.authorization.travelAuthorizationEdit";
    public static final String ERROR_AUTHORIZATION_TR_INITIATION = "error.authorization.travelReimbursementInitiation";
    public static final String ERROR_AUTHORIZATION_TR_EDIT = "error.authorization.travelReimbursementEdit";
    public static final String ERROR_AUTHORIZATION_TR_DELINQUENT = "error.authorization.travelReimbursementDelinquent";
    public static final String ERROR_TRAVEL_DOCUMENT_INITIATION = "error.document.tem.initiation";
    public static final String ERROR_TRAVEL_AGENCY_AUDIT_INITIATION ="error.document.agencyAudit.initiation";

    public static final String MESSAGE_BATCH_UPLOAD_TITLE_PER_DIEM_FILE = "message.batchUpload.title.per.diem.file";
    public static final String MESSAGE_BATCH_UPLOAD_TITLE_PER_DIEM_XML_FILE = "message.batchUpload.title.per.diem.xml.file";

    public static final String ERROR_PER_DIEM_EXISTS = "error.batch.tem.perDiemExists";
    public static final String ERROR_PER_DIEM_MEAL_INCIDENTAL_NON_POSITIVE_AMOUNT = "error.batch.mealIncidental.nonPositiveAmount";    
    
    public static final String MESSAGE_PER_DIEM_REPORT_HEADER = "message.batch.tem.perDiemReportHeader";
    
    public static final String ERROR_ADR_DOCUMENT_NOT_EXIST = "error.tem.adr.documentMustExist";
    
    public static final String TRVL_RELO_REQUESTER_OVERVIEW_ERRORS = "fromStateCode,toStateCode,state,attachmentFile,document.reasonCode,document.jobClsCode,tripOverview.*";
    
    public static final String ERROR_RELO_FROM_STATE_REQUIRED = "error.tem.relocation.fromstate.required";
    public static final String ERROR_RELO_TO_STATE_REQUIRED = "error.tem.relocation.tostate.required";
    public static final String ERROR_ATTACHMENT_REQUIRED = "error.tem.attachment.required";
    public static final String ERROR_RECEIPT_NOTES_REQUIRED = "error.document.tem.actualexpense.receipt.notes";
    
    public static final String TEM_ENT_DOC_ATTENDEE_LIST_QUESTION  = "tem.entertainment.attendee.list.question.text";
    public static final String TEM_ENT_NON_EMPLOYEE_FORM_QUESTION  = "tem.entertainment.NonEmployeeForm.question.text";
    public static final String TEM_ENT_QUESTION_PROCEED="tem.entertainment.question.proceed.text";
    
    public static final String TEM_ENT_HOST_CERTIFICATION  = "tem.entertainment.host.certification";
    
    public static final String MESSAGE_BATCH_UPLOAD_TITLE_AGENCY_DATA_XML_FILE = "message.batchUpload.title.agency.data.xml.file";
    public static final String MESSAGE_BATCH_UPLOAD_TITLE_CREDIT_CARD_DATA_XML_FILE = "message.batchUpload.title.credit.card.data.xml.file";

    public static final String TRVL_RELATED_DOCUMENT = "newAccountingDocumentRelationship*";
    
    public static final String ERROR_TRVL_RELATED_DOCUMENT_REQUIRED = "error.document.tem.travel.relatedDocument.relDocumentNumberRequired";
    public static final String ERROR_TRVL_RELATED_DOCUMENT_NOT_FOUND = "error.document.tem.travel.relatedDocument.documentNotFound";
    
    // upload parser
    public static final String ERROR_UPLOADPARSER_INVALID_FILE_FORMAT = "error.uploadParser.invalidFileFormat";
    public static final String ERROR_UPLOADPARSER_WRONG_PROPERTY_NUMBER = "error.uploadParser.wrongPropertyNumber";
    public static final String ERROR_UPLOADPARSER_INVALID_NUMERIC_VALUE = "error.uploadParser.invalidNumericValue";
    public static final String ERROR_UPLOADPARSER_LINE = "error.uploadParser.line";
    public static final String ERROR_UPLOADPARSER_PROPERTY = "error.uploadParser.property";
    public static final String MESSAGE_UPLOADPARSER_EXCEEDED_MAX_LENGTH = "error.uploadParser.excededMaxLength";
    public static final String MESSAGE_UPLOADPARSER_INVALID_VALUE = "error.uploadParser.invalidValue";
    
    public static final String ERROR_TRVL_GROUP_TRVL_EMP_NOT_FOUND = "error.document.tem.travel.groupTraveler.employeeNotFound";   
    public static final String HOST_CERTIFICATION_REQUIRED_IND = "tem.entertainment.host.certification.required";
    public static final String ERROR_TRIP_TYPE_TA_REQUIRED = "error.document.tem.triptype.ta.required";
    
    public static final String TEM_NON_EMPLOYEE_CERTIFICATION  = "tem.non.employee.certification";
    
  //Agency Data Audit Report
    public static final String MESSAGE_AGENCY_DATA_REPORT_HEADER = "message.batch.tem.agencyDataReportHeader";
    public static final String MESSAGE_AGENCY_DATA_NO_MANDATORY_FIELDS="message.batch.tem.agencyDataMandatoryFields";
    public static final String MESSAGE_AGENCY_DATA_AIR_LODGING_RENTAL_MISSING="message.batch.tem.agencyDataAirLodgingRentalMissing";
    public static final String MESSAGE_AGENCY_DATA_DUPLICATE_RECORD="message.batch.tem.agencyDataDuplicateRecord";
    public static final String MESSAGE_AGENCY_DATA_INVALID_TRAVELER="message.batch.tem.agencyDataInvalidTraveler";
    public static final String MESSAGE_AGENCY_DATA_INVALID_ACCOUNT_NUM="message.batch.tem.agencyDataInvalidAccountNum";
    public static final String MESSAGE_AGENCY_DATA_INVALID_SUBACCOUNT="message.batch.tem.agencyDataInvalidSubAccount";
    public static final String MESSAGE_AGENCY_DATA_INVALID_PROJECT_CODE="message.batch.tem.agencyDataInvalidProjectCode";
    public static final String MESSAGE_AGENCY_DATA_INVALID_OBJECT_CODE="message.batch.tem.agencyDataInvalidObjectCode";
    public static final String MESSAGE_AGENCY_DATA_INVALID_SUB_OBJECT_CODE="message.batch.tem.agencyDataInvalidSubObjectCode";
    public static final String MESSAGE_AGENCY_DATA_INVALID_ACCTG_INFO="message.batch.tem.agencyDataInvalidAcctgInfo";
    public static final String MESSAGE_AGENCY_DATA_INVALID_TRIP_ID="message.batch.tem.agencyDataInvalidTripId";
    public static final String MESSAGE_AGENCY_DATA_INVALID_CCA="message.batch.tem.agencyDataInvalidCreditCardAgency";
    public static final String MESSAGE_AGENCY_DATA_MISSING_COA="message.batch.tem.agencyDataMissingChartCode";
    public static final String MESSAGE_AGENCY_DATA_MISSING_ACCOUNT_NUM="message.batch.tem.agencyDataMissingAccountNumber";
    public static final String MESSAGE_AGENCY_DATA_MISSING_TRIP_DATA="message.batch.tem.agencyDataMissingTripData";
    
    public static final String ERROR_TEM_DISTRIBUTION_ACCOUNTING_LINES_TOTAL="error.tem.distribution.accountingline.total";
    public static final String ERROR_TEM_DISTRIBUTION_ACCOUNTING_LINES_AMOUNT_OR_PERCENT="error.tem.distribution.accountingline.amount.or.percent";
    public static final String ERROR_TEM_ACCOUNTING_LINES_OBJECT_CODE_CARD_TYPE="error.tem.accountingline.objectcode";
    public static final String ERROR_TEM_ACCOUNTING_LINES_OBJECT_CODE_CARD_TYPE_TOTAL="error.tem.accountingline.objectcode.total";

    
    public static final String ERROR_TEM_IMPORT_EXPENSES_PROFILE_MISSING="error.document.tem.travel.import.profile.missing";
    public static final String ERROR_TEM_IMPORT_EXPENSES_PRIMARY_DESTINATION_MISSING="error.document.tem.travel.import.primary.destination.missing";
    
    public static final String INFO_TEM_IMPORT_CURRENCY_CONVERSION="info.document.tem.travel.import.currency.conversion";
    public static final String INFO_TEM_IMPORT_DOCUMENT_SAVE="info.document.auto.save";
    
    public static final String ERROR_TEM_DETAIL_GREATER_THAN_EXPENSE="error.document.detail.greater.than.expense";
    public static final String ERROR_TEM_DETAIL_LESS_THAN_EXPENSE="error.document.detail.less.than.expense";
    public static final String ERROR_TEM_DETAIL_LESS_THAN_ZERO="error.document.detail.less.than.zero";
    
    //TMCP errors
    public static final String ERROR_TMCP_SYSTEMFIELD_REQUIRED = "error.tem.correction.systemfield.required";
    public static final String ERROR_TMCP_EDITMETHODFIELD_REQUIRED = "error.tem.correction.editmethodfield.required";
    public static final String ERROR_TMCP_AGENCYGROUP_REQUIRED = "error.tem.correction.AGENCYgroup.required";
    public static final String ERROR_TMCP_AGENCYGROUP_REQUIRED_FOR_ROUTING = "error.tem.correction.AGENCYgroup.required.for.route";
    public static final String ERROR_TMCP_NO_RECORDS = "error.tem.correction.norecords";
    public static final String ERROR_TMCP_INVALID_VALUE = "error.tem.correction.invalid.value";
    public static final String ERROR_TMCP_UNABLE_TO_MANUAL_EDIT_LARGE_GROUP = "error.tem.correction.unable.to.manual.edit.large.group";
    public static final String ERROR_TMCP_UNABLE_TO_MANUAL_EDIT_ANY_GROUP = "error.tem.correction.unable.to.manual.edit.any.group";
    public static final String ERROR_TMCP_INVALID_SYSTEM_OR_EDIT_METHOD_CHANGE = "error.tem.correction.invalid.system.or.edit.method.change";
    public static final String ERROR_TMCP_INVALID_INPUT_GROUP_CHANGE = "error.tem.correction.invalid.input.group.change";
    public static final String ERROR_TMCP_PERSISTED_AGENCY_ENTRIES_MISSING = "error.tem.correction.persisted.agency.entries.missing";
    public static final String ERROR_TMCP_REMOVE_GROUP_REQUIRES_DATABASE = "error.tem.correction.remove.group.requires.database";

    public static final String ERROR_DOCUMENT_TOTAL_ESTIMATED = "error.document.tem.total.estimated";
    public static final String ERROR_ACCOUNTING_LINE_CG = "error.tem.accountingline.cg.accountnumber";
}
