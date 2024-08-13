package clojure_general_ms;

public class Test {


    public void execute(String input) {
        if (!input.equals("OK")) {
            caseOk();
        } else {
            caseNotOk();
        }
    }

    public void caseOk() {
        System.out.println("caseOk");
    }

    public void caseNotOk() {
        System.out.println("caseNotOk");
    }

}
