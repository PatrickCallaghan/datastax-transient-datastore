package com.datastax.datastore;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.datastore.dao.MessageObject;
import com.datastax.demo.utils.PropertyHelper;
import com.datastax.driver.core.utils.UUIDs;

public class MessageMain {

	private static Logger logger = LoggerFactory.getLogger(MessageMain.class);
	
	
	public MessageMain() {

		BlockingQueue<MessageObject> writerQueue = new ArrayBlockingQueue<MessageObject>(1000);
		BlockingQueue<MessageObject> readerQueue = new ArrayBlockingQueue<MessageObject>(1000);
		
		//Executor for Threads
		int noOfThreads = Integer.parseInt(PropertyHelper.getProperty("noOfThreads", "10"));
		ExecutorService readerExecutor = Executors.newFixedThreadPool(noOfThreads);		
		ExecutorService writerExecutor = Executors.newFixedThreadPool(noOfThreads);
		
		DataStoreMessageService service = new DataStoreMessageService();
		
		for (int i = 0; i < noOfThreads; i++){		
			writerExecutor.submit(new MessageWriter(writerQueue, readerQueue, service));
		}

		for (int i = 0; i < noOfThreads; i++){
			readerExecutor.submit(new MessageReader(readerQueue, service));		
		}

		while (true){
			
            MessageObject data = new MessageObject();
            data.setTransactionId(UUID.randomUUID().toString());
            
            data.setMsgId(UUID.randomUUID().toString());
            data.setId(UUIDs.timeBased());
            data.setLegId(1);
            data.setSettlementDate(SETTLEMENT_DATE);
            data.setSettlementCycle(3);
            data.setAmount(500);
            data.setCreditorId(CREDITOR_ID_123456789);
            data.setDebtorId(DEBTOR_ID_123456789);
            data.setInstructingId(INSTRUCTING_ID_123456789);
            data.setInstructedId(INSTRUCTED_ID_123456789);
            data.setCreditorAgentId(CREDITOR_AGENT_ID_123456789);
            data.setDebtorAgentId(DEBTOR_AGENT_ID_123456789);
            data.setRawMessage(RAW_MESSAGE);
                   
			try {
				writerQueue.put(data);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
		
	


    private static final String RAW_MESSAGE = "{\t\"FIToFICstmrCdtTrf\": {\t\t\"CdtTrfTxInf\": [{\t\t\t\"Cdtr\": {\t\t\t\t\"CtctDtls\": {\t\t\t\t\t\"Nm\": \"Creditor Name\"\t\t\t\t},\t\t\t\t\"Id\": {\t\t\t\t\t\"OrgId\": {\t\t\t\t\t\t\"AnyBIC\": \"NWBKGB2105P\"\t\t\t\t\t},\t\t\t\t\t\"PrvtId\": {\t\t\t\t\t\t\"DtAndPlcOfBirth\": {\t\t\t\t\t\t\t\"BirthDt\": \"2018-06-19\",\t\t\t\t\t\t\t\"CityOfBirth\": \"London\",\t\t\t\t\t\t\t\"CtryOfBirth\": \"UK\"\t\t\t\t\t\t}\t\t\t\t\t}\t\t\t\t}\t\t\t},\t\t\t\"CdtrAcct\": {\t\t\t\t\"Ccy\": \"GBP\",\t\t\t\t\"Id\": {\t\t\t\t\t\"IBAN\": \"GB29NWBK60161331926819\"\t\t\t\t}\t\t\t},\t\t\t\"CdtrAgt\": {\t\t\t\t\"FinInstnId\": {\t\t\t\t\t\"BICFI\": \"NWBKGB2105P\",\t\t\t\t\t\"ClrSysMmbId\": {\t\t\t\t\t\t\"MmbId\": \"020010001\"\t\t\t\t\t}\t\t\t\t}\t\t\t},\t\t\t\"ChrgBr\": \"SLEV\",\t\t\t\"Dbtr\": {\t\t\t\t\"CtctDtls\": {\t\t\t\t\t\"Nm\": \"Debtor name\"\t\t\t\t},\t\t\t\t\"PstlAdr\": {\t\t\t\t\t\"BldgNb\": \"46\",\t\t\t\t\t\"Ctry\": \"UK\",\t\t\t\t\t\"PstCd\": \"SG12 0BT\",\t\t\t\t\t\"StrtNm\": \"Canons Road\",\t\t\t\t\t\"TwnNm\": \"Ware\"\t\t\t\t}\t\t\t},\t\t\t\"DbtrAcct\": {\t\t\t\t\"Ccy\": \"GBP\",\t\t\t\t\"Id\": {\t\t\t\t\t\"IBAN\": \"GB92BARC20005275849855\"\t\t\t\t}\t\t\t},\t\t\t\"DbtrAgt\": {\t\t\t\t\"FinInstnId\": {\t\t\t\t\t\"BICFI\": \"BARCGB2102L\",\t\t\t\t\t\"ClrSysMmbId\": {\t\t\t\t\t\t\"MmbId\": \"021200201\"\t\t\t\t\t}\t\t\t\t}\t\t\t},\t\t\t\"InstdAgt\": {\t\t\t\t\"FinInstnId\": {\t\t\t\t\t\"BICFI\": \"BARCGB2104T\",\t\t\t\t\t\"ClrSysMmbId\": {\t\t\t\t\t\t\"MmbId\": \"021200202\"\t\t\t\t\t}\t\t\t\t}\t\t\t},\t\t\t\"InstgAgt\": {\t\t\t\t\"FinInstnId\": {\t\t\t\t\t\"BICFI\": \"NWBKGB2105P\",\t\t\t\t\t\"ClrSysMmbId\": {\t\t\t\t\t\t\"MmbId\": \"020010001\"\t\t\t\t\t}\t\t\t\t}\t\t\t},\t\t\t\"IntrBkSttlmAmt\": {\t\t\t\t\"Ccy\": \"GBP\",\t\t\t\t\"value\": 525.25\t\t\t},\t\t\t\"IntrBkSttlmDt\": \"2018-06-19\",\t\t\t\"PmtId\": {\t\t\t\t\"EndToEndId\": \"E2E-Ref001\",\t\t\t\t\"TxId\": \"20151115021200201BCPA0000000001\"\t\t\t},\t\t\t\"PmtTpInf\": {\t\t\t\t\"LclInstrm\": {\t\t\t\t\t\"Prtry\": \"BUSINESS\"\t\t\t\t},\t\t\t\t\"SvcLvl\": {\t\t\t\t\t\"Cd\": \"SDVA\"\t\t\t\t}\t\t\t},\t\t\t\"RmtInf\": {\t\t\t\t\"Ustrd\": [\"Unstructured Remittance Information\"]\t\t\t}\t\t}],\t\t\"GrpHdr\": {\t\t\t\"CreDtTm\": \"2018-06-19T12:21:54.881Z\",\t\t\t\"IntrBkSttlmDt\": \"2018-06-19\",\t\t\t\"MsgId\": \"M20151112021200201BFF0000000001\",\t\t\t\"NbOfTxs\": \"1\",\t\t\t\"SttlmInf\": {\t\t\t\t\"ClrSys\": {\t\t\t\t\t\"Cd\": \"MACH1\"\t\t\t\t},\t\t\t\t\"SttlmMtd\": \"INDA\",\t\t\t\t\"TtlIntrBkSttlmAmt\": {\t\t\t\t\t\"Ccy\": \"GBP\",\t\t\t\t\t\"value\": 525.25\t\t\t\t}\t\t\t}\t\t}\t}}";
    private static final String SETTLEMENT_DATE = "11/11/2011";
    private static final String CREDITOR_ID_123456789 = "creditorId-123456789";
    private static final String DEBTOR_ID_123456789 = "debtorId-123456789";
    private static final String INSTRUCTING_ID_123456789 = "instructingId-123456789";
    private static final String INSTRUCTED_ID_123456789 = "instructedId-123456789";
    private static final String CREDITOR_AGENT_ID_123456789 = "creditorAgentId-123456789";
    private static final String DEBTOR_AGENT_ID_123456789 = "debtorAgentId-123456789";
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MessageMain();

		System.exit(0);
	}
}
