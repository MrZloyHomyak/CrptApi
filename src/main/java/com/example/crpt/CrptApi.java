package main.java.com.example.crpt;

import java.net.http.HttpClient;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CrptApi {
    private final TimeUnit timeUnit;
    private final int requestLimit;
    private final ConcurrentLinkedQueue<Instant> requestTimestamps;
    private final HttpClient httpClient;
    private final ReentrantLock lock;
    private final String apiUrl;
    private final String defaultProductGroup;

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this(timeUnit, requestLimit, "clothes");
    }

    public CrptApi(TimeUnit timeUnit, int requestLimit, String defaultProductGroup) {
        this(timeUnit, requestLimit, "https://ismp.crpt.ru/api/v3/lk/documents/create", defaultProductGroup);
    }

    public CrptApi(TimeUnit timeUnit, int requestLimit, String apiUrl, String defaultProductGroup) {
        if (requestLimit <= 0) {
            throw new IllegalArgumentException("Request limit must be positive");
        }
        this.timeUnit = timeUnit;
        this.requestLimit = requestLimit;
        this.apiUrl = apiUrl;
        this.defaultProductGroup = defaultProductGroup;
        this.requestTimestamps = new ConcurrentLinkedQueue<>();
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        this.lock = new ReentrantLock();
    }

    public void createDocument(Document document, String signature) {

    }




    public static class Document {
        private Description description;
        private String doc_id;
        private String doc_status;
        private String doc_type;
        private String importRequest;
        private String owner_inn;
        private String participant_inn;
        private String producer_inn;
        private String production_date;
        private String production_type;
        private Product[] products;
        private String reg_date;
        private String reg_number;

        public Document() {}

        public String toJsonString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");

            if (description != null) {
                sb.append("\"description\":").append(description.toJsonString()).append(",");
            } else {
                sb.append("\"description\":null,");
            }
            appendField(sb, "doc_id", doc_id, true);
            appendField(sb, "doc_status", doc_status, true);
            appendField(sb, "doc_type", doc_type, true);
            appendField(sb, "importRequest", importRequest, true);
            appendField(sb, "owner_inn", owner_inn, true);
            appendField(sb, "participant_inn", participant_inn, true);
            appendField(sb, "producer_inn", producer_inn, true);
            appendField(sb, "production_date", production_date, true);
            appendField(sb, "production_type", production_type, true);

            if (products != null && products.length > 0) {
                sb.append("\"products\":[");
                for(int i = 0; i < products.length; i++) {
                    if(i > 0) sb.append(",");
                    sb.append(products[i].toJsonString());
                }
                sb.append("]");
            } else {
                sb.append("\"products\":[],");
            }
            appendField(sb, "reg_date", reg_date, true);
            appendField(sb, "reg_number", reg_number, false);
            sb.append("}");
            return sb.toString();
        }

        private void appendField(StringBuilder sb, String fieldName, String value, boolean addComa) {
            if (value != null) {
                sb.append(String.format("\"%s\":\"%s\"", fieldName, escapeJsonString(value)));
            }
        }

        private String escapeJsonString(String value) {
            if (value == null) {
                return "";
            } else {
                return value.replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\b", "\\b")
                        .replace("\f", "\\f")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r")
                        .replace("\t", "\\t");
            }
        }

        public Description getDescription() {
            return description;
        }
        public void setDescription(Description description) {
            this.description = description;
        }
        public String getDoc_id() {
            return doc_id;
        }
        public void setDoc_id(String doc_id) {
            this.doc_id = doc_id;
        }
        public String getDoc_status() {
            return doc_status;
        }
        public void setDoc_status(String doc_status) {
            this.doc_status = doc_status;
        }
        public String getDoc_type() {
            return doc_type;
        }
        public void setDoc_type(String doc_type) {
            this.doc_type = doc_type;
        }
        public String getImportRequest() {
            return importRequest;
        }
        public void setImportRequest(String importRequest) {
            this.importRequest = importRequest;
        }
        public String getOwner_inn() {
            return owner_inn;
        }
        public void setOwner_inn(String owner_inn) {
            this.owner_inn = owner_inn;
        }
        public String getParticipant_inn() {
            return participant_inn;
        }
        public void setParticipant_inn(String participant_inn) {
            this.participant_inn = participant_inn;
        }
        public String getProducer_inn() {
            return producer_inn;
        }
        public void setProducer_inn(String producer_inn) {
            this.producer_inn = producer_inn;
        }
        public String getProduction_date() {
            return production_date;
        }
        public void setProduction_date(String production_date) {
            this.production_date = production_date;
        }
        public String getProduction_type() {
            return production_type;
        }
        public void setProduction_type(String production_type) {
            this.production_type = production_type;
        }
        public Product[] getProducts() {
            return products;
        }
        public void setProducts(Product[] products) {
            this.products = products;
        }
        public String getReg_date() {
            return reg_date;
        }
        public void setReg_date(String reg_date) {
            this.reg_date = reg_date;
        }
        public String getReg_number() {
            return reg_number;
        }
        public void setReg_number(String reg_number) {
            this.reg_number = reg_number;
        }




    }

    public static class Description {
        private String participant_inn;

        public Description() {}
        public Description(String participant_inn) {
            this.participant_inn = participant_inn;
        }
        public String getParticipant_inn() {
            return participant_inn;
        }
        public void setParticipant_inn(String participant_inn) {
            this.participant_inn = participant_inn;
        }
        public String toJsonString() {
            return String.format("{\"participant_inn\":\"%s\"}", participant_inn);
        }
        public String escapeJsonString(String value) {
            if(value == null) return "";
            return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
        }
    }

    public static class Product {
        private String certificate_document;
        private String certificate_document_date;
        private String certificate_document_number;
        private String owner_inn;
        private String producer_inn;
        private String production_date;
        private String tnved_code;
        private String uit_code;
        private String uitu_code;

        public Product() {}

        public String getCertificate_document() {
            return certificate_document;
        }
        public void setCertificate_document(String certificate_document) {
            this.certificate_document = certificate_document;
        }
        public String getCertificate_document_date() {
            return certificate_document_date;
        }
        public void setCertificate_document_date(String certificate_document_date) {
            this.certificate_document_date = certificate_document_date;
        }
        public String getCertificate_document_number() {
            return certificate_document_number;
        }
        public void setCertificate_document_number(String certificate_document_number) {
            this.certificate_document_number = certificate_document_number;
        }
        public String getOwner_inn() {
            return owner_inn;
        }
        public void setOwner_inn(String owner_inn) {
            this.owner_inn = owner_inn;
        }
        public String getProducer_inn() {
            return producer_inn;
        }
        public void setProducer_inn(String producer_inn) {
            this.producer_inn = producer_inn;
        }
        public String getProduction_date() {
            return production_date;
        }
        public void setProduction_date(String production_date) {
            this.production_date = production_date;
        }
        public String getTnved_code() {
            return tnved_code;
        }
        public void setTnved_code(String tnved_code) {
            this.tnved_code = tnved_code;
        }
        public String getUit_code() {
            return uit_code;
        }
        public void setUit_code(String uit_code) {
            this.uit_code = uit_code;
        }
        public String getUitu_code() {
            return uitu_code;
        }
        public void setUitu_code(String uitu_code) {
            this.uitu_code = uitu_code;
        }


        public String toJsonString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            appendField(sb, "certificate_document", certificate_document, true);
            appendField(sb, "certificate_document_date", certificate_document_date, true);
            appendField(sb, "certificate_document_number", certificate_document_number, true);
            appendField(sb, "owner_inn", owner_inn, true);
            appendField(sb, "producer_inn", producer_inn, true);
            appendField(sb, "production_date", production_date, true);
            appendField(sb, "tnved_code", tnved_code, true);
            appendField(sb, "uit_code", uit_code, true);
            appendField(sb, "uitu_code", uitu_code, false);

            sb.append("}");
            return sb.toString();
        }

        private void appendField(StringBuilder sb, String fieldName, String value, boolean addComma) {
            if (value != null) {
                sb.append(String.format("\"%s\":\"%s\"", fieldName, escapeJsonString(value)));
                if (addComma) sb.append(",");
            }
        }

        public String escapeJsonString(String value) {
            if (value == null) {
                return "";
            }
            return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
        }


    }

    public static void main(String[] args) {

    }
}
