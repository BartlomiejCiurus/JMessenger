package pl.jasnastronakodu.Server;

class MainServer {

    public static void main(String[] args) {
        new LoginServer().start();
        new CreateAccountServer().start();
        new GetContactsServer().start();
        new AddContactServer().start();
        new DeleteContactServer().start();
        new SaveHistoryServer().start();
        new GetHistoryServer().start();
    }

}
