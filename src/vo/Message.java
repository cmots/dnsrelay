package vo;

public class Message {
    private int transactionID;
    private byte[] flags; //2 byte
    private int QR;  //0 is query, 1 is response, 1 bit
    private int relyCode; //0 is no error, 3 is no such name, 4 bits

    //2 bytes x 4
    private int questions;
    private int answerRRs;
    private int authorityRRs;
    private int additionalRRs;

    private String queryName;
    private int queryType;  //A(1) is ipv4,  AAAA(28) is ipv6, 2bytes
    private int queryClass; //0x0001 don't change this!, 2bytes

    private String answerName; // c00c
    private int answerType = 1; //A(1) 2bytes
    private int answerClass = 1; //0x0001 don't change this! 2bytes
    private int TTL = 1024;        //256 in initiallzation, 4bytes
    private int datalength = 4;  //ipv4 is 4, 2bytes
    private String address;

    private byte[] originData;

    private String clientAddress ;
    private int clientPort ;

    public Message (byte[] packetData) {
        this.queryName = "";
        resolve(packetData);
    }

    public void resolve(byte[] packetData) {
        byte[] target = new byte[2];


        originData = packetData.clone();
        flags = new byte[2];
        flags[0] = this.originData[2];
        flags[1] = this.originData[3];
        System.arraycopy(originData, 0, target, 0, 2);
        transactionID = target[0]*256 + target[1];

        QR = flags[0] & 0xff & 0b10000000;
        relyCode = flags[1] & 0xff & 0b00001111;

        System.arraycopy(originData, 4, target, 0, 2);
        questions = (target[0]&0xff)*256 + (target[1]&0xff);
        System.arraycopy(originData, 6, target, 0, 2);
        answerRRs = (target[0]&0xff)*256 + (target[1]&0xff);
        System.arraycopy(originData, 8, target, 0, 2);
        authorityRRs = (target[0]&0xff)*256 + (target[1]&0xff);
        System.arraycopy(originData, 10, target, 0, 2);
        additionalRRs = (target[0]&0xff)*256 + (target[1]&0xff);

        int i = 12;


        while(this.originData[i]!=0) {
            int num = this.originData[i];
            target = new byte[num];
            System.arraycopy(originData, ++i, target, 0, num);
            queryName += new String(target);
            i+=num;
            if(this.originData[i]!=0)
                queryName += ".";
        }
        i++;
        queryType = (this.originData[i++]&0xff)*256 + (this.originData[i++]&0xff);
        queryClass = (this.originData[i++]&0xff)*256 + (this.originData[i++]&0xff);

    }

    public byte[] makePacket(boolean rebuildAnswer) {
        byte[] target = new byte[512];
        //transaction ID
        target[0] = (byte) (this.transactionID>>8);
        target[1] = (byte) (this.transactionID);
        //transaction flags
        target[2] = (byte) ((flags[0] & 0b01111111) + (byte)(QR<<7));
        target[3] = (byte) ((flags[1] & 0b11110000) + relyCode);
        //questions
        target[4] = (byte) (questions>>8);
        target[5] = (byte) (questions);
        //answerRRs
        target[6] = (byte) (answerRRs>>8);
        target[7] = (byte) (answerRRs);
        //authorityRRs
        target[8] = (byte) (authorityRRs>>8);
        target[9] = (byte) (authorityRRs);
        //additionalRRs
        target[10] = (byte) (additionalRRs>>8);
        target[11] = (byte) (additionalRRs);

        //query
        int i = 12;
        String[] tokens = queryName.split("\\.");
        for(int j=0;j<tokens.length;j++) {
            target[i++] = (byte) (tokens[j].length());
            byte[] tokenByte = tokens[j].getBytes();
            for(int k=0;k<tokenByte.length;k++)
                target[i++] = tokenByte[k];
        }
        target[i++] = (byte) (0);

        target[i++] = (byte) (queryType>>8);
        target[i++] = (byte) (queryType);

        target[i++] = (byte) (queryClass>>8);
        target[i++] = (byte) (queryClass);

        if(rebuildAnswer) {
            target[i++] = (byte) (0xc0);
            target[i++] = (byte) (0x0c);

            target[i++] = (byte) (answerType>>8);
            target[i++] = (byte) (answerType);

            target[i++] = (byte) (answerClass>>8);
            target[i++] = (byte) (answerClass);

            target[i++] = (byte) (TTL>>24);
            target[i++] = (byte) (TTL>>16);
            target[i++] = (byte) (TTL>>8);
            target[i++] = (byte) (TTL);

            target[i++] = (byte) (datalength>>8);
            target[i++] = (byte) (datalength);
            tokens = address.split("\\.");
            for(int j=0;j<tokens.length;j++) {
                int token = Integer.valueOf( tokens[j] );
                target[i++] = (byte) (token & 0xff);
            }


            target = java.util.Arrays.copyOf(target,i);
            return target;
        }
        else {
            for(int j=0;j<i;j++)
                this.originData[j] = target[j];
            return this.originData;
        }
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public byte[] getFlags() {
        return flags;
    }

    public void setFlags(byte[] flags) {
        this.flags = flags;
    }

    public int getQR() {
        return QR;
    }

    public void setQR(int QR) {
        this.QR = QR;
    }

    public int getRelyCode() {
        return relyCode;
    }

    public void setRelyCode(int relyCode) {
        this.relyCode = relyCode;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }

    public int getAnswerRRs() {
        return answerRRs;
    }

    public void setAnswerRRs(int answerRRs) {
        this.answerRRs = answerRRs;
    }

    public int getAuthorityRRs() {
        return authorityRRs;
    }

    public void setAuthorityRRs(int authorityRRs) {
        this.authorityRRs = authorityRRs;
    }

    public int getAdditionalRRs() {
        return additionalRRs;
    }

    public void setAdditionalRRs(int additionalRRs) {
        this.additionalRRs = additionalRRs;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public int getQueryClass() {
        return queryClass;
    }

    public void setQueryClass(int queryClass) {
        this.queryClass = queryClass;
    }

    public String getAnswerName() {
        return answerName;
    }

    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    public int getAnswerType() {
        return answerType;
    }

    public void setAnswerType(int answerType) {
        this.answerType = answerType;
    }

    public int getAnswerClass() {
        return answerClass;
    }

    public void setAnswerClass(int answerClass) {
        this.answerClass = answerClass;
    }

    public int getTTL() {
        return TTL;
    }

    public void setTTL(int TTL) {
        this.TTL = TTL;
    }

    public int getDatalength() {
        return datalength;
    }

    public void setDatalength(int datalength) {
        this.datalength = datalength;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getOriginData() {
        return originData;
    }

    public void setOriginData(byte[] originData) {
        this.originData = originData;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }
}
