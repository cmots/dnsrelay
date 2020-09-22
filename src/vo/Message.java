package vo;

public class Message {
    private int ID;
    private byte[] temp;
    private int QR;
    private int Rcode;
    
    private int questionCount;
    private int answerCount;
    private int authorityCount;
    private int additionalCount;

    private String domainName;
    private int domainType;
    private int domainClass;
    private int TTL = 1024;

    private String answerName;
    private int answerType = 1;
    private int answerClass = 1;

    private int dataLength = 4;


    private String address;

    private byte[] originData;

    private String clientAddress ;
    private int clientPort ;

    public Message (byte[] packetData) {
        this.domainName = "";
        resolve(packetData);
    }

    public void resolve(byte[] packetData) {
        byte[] target = new byte[2];


        originData = packetData.clone();
        temp = new byte[2];
        temp[0] = this.originData[2];
        temp[1] = this.originData[3];
        System.arraycopy(originData, 0, target, 0, 2);
        ID = target[0]*256 + target[1];

        QR = temp[0] & 0xff & 0b10000000;
        Rcode = temp[1] & 0xff & 0b00001111;

        System.arraycopy(originData, 4, target, 0, 2);
        questionCount = (target[0]&0xff)*256 + (target[1]&0xff);
        System.arraycopy(originData, 6, target, 0, 2);
        answerCount = (target[0]&0xff)*256 + (target[1]&0xff);
        System.arraycopy(originData, 8, target, 0, 2);
        authorityCount = (target[0]&0xff)*256 + (target[1]&0xff);
        System.arraycopy(originData, 10, target, 0, 2);
        additionalCount = (target[0]&0xff)*256 + (target[1]&0xff);

        int i = 12;


        while(this.originData[i]!=0) {
            int num = this.originData[i];
            target = new byte[num];
            System.arraycopy(originData, ++i, target, 0, num);
            domainName += new String(target);
            i+=num;
            if(this.originData[i]!=0)
                domainName += ".";
        }
        i++;
        domainType = (this.originData[i++]&0xff)*256 + (this.originData[i++]&0xff);
        domainClass = (this.originData[i++]&0xff)*256 + (this.originData[i++]&0xff);

    }

    public byte[] makePacket(boolean rebuildAnswer) {
        byte[] target = new byte[512];
        //transaction ID
        target[0] = (byte) (this.ID >>8);
        target[1] = (byte) (this.ID);
        //transaction flags
        target[2] = (byte) ((temp[0] & 0b01111111) + (byte)(QR<<7));
        target[3] = (byte) ((temp[1] & 0b11110000) + Rcode);
        //questions
        target[4] = (byte) (questionCount >>8);
        target[5] = (byte) (questionCount);
        //answerRRs
        target[6] = (byte) (answerCount >>8);
        target[7] = (byte) (answerCount);
        //authorityRRs
        target[8] = (byte) (authorityCount >>8);
        target[9] = (byte) (authorityCount);
        //additionalRRs
        target[10] = (byte) (additionalCount >>8);
        target[11] = (byte) (additionalCount);

        //query
        int i = 12;
        String[] tokens = domainName.split("\\.");
        for(int j=0;j<tokens.length;j++) {
            target[i++] = (byte) (tokens[j].length());
            byte[] tokenByte = tokens[j].getBytes();
            for(int k=0;k<tokenByte.length;k++)
                target[i++] = tokenByte[k];
        }
        target[i++] = (byte) (0);

        target[i++] = (byte) (domainType >>8);
        target[i++] = (byte) (domainType);

        target[i++] = (byte) (domainClass >>8);
        target[i++] = (byte) (domainClass);

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

            target[i++] = (byte) (dataLength >>8);
            target[i++] = (byte) (dataLength);
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public byte[] getTemp() {
        return temp;
    }

    public void setTemp(byte[] temp) {
        this.temp = temp;
    }

    public int getQR() {
        return QR;
    }

    public void setQR(int QR) {
        this.QR = QR;
    }

    public int getRcode() {
        return Rcode;
    }

    public void setRcode(int rcode) {
        this.Rcode = rcode;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getAuthorityCount() {
        return authorityCount;
    }

    public void setAuthorityCount(int authorityCount) {
        this.authorityCount = authorityCount;
    }

    public int getAdditionalCount() {
        return additionalCount;
    }

    public void setAdditionalCount(int additionalCount) {
        this.additionalCount = additionalCount;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public int getDomainType() {
        return domainType;
    }

    public void setDomainType(int domainType) {
        this.domainType = domainType;
    }

    public int getDomainClass() {
        return domainClass;
    }

    public void setDomainClass(int domainClass) {
        this.domainClass = domainClass;
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

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
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
