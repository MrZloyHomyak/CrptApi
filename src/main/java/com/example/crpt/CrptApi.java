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
        private boolean importRequest;
        private String owner_inn;
        private String participant_inn;
        private String producer_inn;
        private String production_date;
        private String production_type;
        private Product[] products;
        private String reg_date;
        private String reg_number;


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

        private void appendField(StringBuilder sb, String fieldName, String value, boolean addComma) {
            if (value != null) {
                sb.append(String.format("\"%s\":\"%s\"", fieldName, escapeJsonString(value)));
                if (addComma) sb.append(",");
            }
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
