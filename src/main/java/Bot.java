import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {
    public String getBotToken() {
        return "{botToken}";
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            String text = message.getText().toLowerCase();
            switch (text) {
                case "/help":
                    //some code
                    break;
                case "/settings":
                    //some code
                    break;
                default:
                    try {
                        sendMsg(message, ScheduleParser.parse(text));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }


    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableHtml(true);
        sendMessage.setChatId(message.getChatId()); // maybe here should be .toString() after getChatdId()
        sendMessage.setText(text);
        System.out.println(message.getChatId());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return null;
    }

    public static void main(String[] args)   {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }
}
