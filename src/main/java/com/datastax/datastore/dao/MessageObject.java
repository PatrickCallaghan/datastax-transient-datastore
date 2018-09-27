package com.datastax.datastore.dao;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace="messages", name="message")
public class MessageObject {

	@PartitionKey
    private String transactionId;
    private String msgId;
    
    @ClusteringColumn(0)
    private UUID id;
    
    @ClusteringColumn(1)
    private int legId;
    private String settlementDate;
    private int settlementCycle;
    private int amount;
    private String creditorId;
    private String debtorId;
    private String instructingId;
    private String instructedId;
    private String creditorAgentId;
    private String debtorAgentId;
    private String rawMessage;
    
	public MessageObject() {
		super();
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public int getLegId() {
		return legId;
	}
	public void setLegId(int legId) {
		this.legId = legId;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	public int getSettlementCycle() {
		return settlementCycle;
	}
	public void setSettlementCycle(int settlementCycle) {
		this.settlementCycle = settlementCycle;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCreditorId() {
		return creditorId;
	}
	public void setCreditorId(String creditorId) {
		this.creditorId = creditorId;
	}
	public String getDebtorId() {
		return debtorId;
	}
	public void setDebtorId(String debtorId) {
		this.debtorId = debtorId;
	}
	public String getInstructingId() {
		return instructingId;
	}
	public void setInstructingId(String instructingId) {
		this.instructingId = instructingId;
	}
	public String getInstructedId() {
		return instructedId;
	}
	public void setInstructedId(String instructedId) {
		this.instructedId = instructedId;
	}
	public String getCreditorAgentId() {
		return creditorAgentId;
	}
	public void setCreditorAgentId(String creditorAgentId) {
		this.creditorAgentId = creditorAgentId;
	}
	public String getDebtorAgentId() {
		return debtorAgentId;
	}
	public void setDebtorAgentId(String debtorAgentId) {
		this.debtorAgentId = debtorAgentId;
	}
	public String getRawMessage() {
		return rawMessage;
	}
	public void setRawMessage(String rawMessage) {
		this.rawMessage = rawMessage;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
    
    
   
}
