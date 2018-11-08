package client.view;

public class Input {

    private final String SEPARATOR = " ";
    private String userInput;
    private String command = "";
    private String paramOne = "0";
    private String paramTwo = "0";

    public Input(String userInput) {
        this.userInput = userInput;
        this.parseCommand();
    }

    public String getCommand() {
        return command;
    }

    public String getParamOne() {
        return paramOne;
    }

    public String getParamTwo() {
        return paramTwo;
    }

    private void parseCommand() {
        String[] cmdAndParam = userInput.split(SEPARATOR);
        if(cmdAndParam.length > 0){
            this.command = cmdAndParam[0].toUpperCase();
            if(cmdAndParam.length > 1) {
                this.paramOne = cmdAndParam[1].toUpperCase();
                if (cmdAndParam.length > 2) {
                    this.paramTwo = cmdAndParam[2].toUpperCase();
                }
            }
        }
    }
}
