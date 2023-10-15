package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    public String getBotUsername() {
        return "JavaTest_bot";
    }

    public String getBotToken() {
        return "6499958090:AAGJ6g_v8XIJfRYiTgIThoPZ6d_FyDz96JU";
    }

    human bot = new human();
    private final double version = 1.00;

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = message.getText();
        long user_id = message.getFrom().getId();
        String username = message.getFrom().getFirstName();

        if (text.equals("/start")) {
            sendText(user_id, "Привет, " + username + "!");
            sendText(user_id, "bot v" + version);
            sendText(user_id, askQuestion(bot.getQuestion()));
        } else {
            boolean result = checkAnswer(bot.getQuestion(), text);
            if (result) {
                sendText(user_id, "Верно!");
                bot.setCount(bot.getCount() + 1);
                bot.setAnswersOnQuestion(true);
            } else {
                sendText(user_id, "Неверно!");
                bot.setAnswersOnQuestion(false);
            }
            bot.setQuestion(bot.getQuestion() + 1);
            if (bot.getQuestion() <= 4) {
                String question = askQuestion(bot.getQuestion());
                sendText(user_id, question);
            } else {
                sendText(user_id, "Игра окончена!");
                sendText(user_id, "Баллов набрано: " + bot.getCount());
                String validAnswers = "Вы верно ответили на следующие вопросы: ";
                for (var i : bot.getAnswersOnQuestion().entrySet()) {
                    if (i.getValue()) {
                        validAnswers = validAnswers + i.getKey() + " ";
                    }
                }
                sendText(user_id, validAnswers);
                bot.clear();
            }
        }
    }

    public String askQuestion(int number){
        if (number == 1){
            return "Вопрос 1. Сколько в ЯП Java есть примитивов?";
        }
        else if (number==2){
            return "Вопрос 2. Сколько в реляционных(SQL) БД существует типов связей между таблицами?";
        }
        else if (number==3){
            return "Вопрос 3. С помощью какой команды в Git можно просмотреть авторов различных строк в одном файле?";
        }
        else
            return "Вопрос 4. Какие типы HTTP-запросов Вы знаете?";
    }

    public boolean checkAnswer(int number, String answer){
        answer = answer.toLowerCase();

        if (number == 1)
            return answer.equals("8");
        if (number == 2)
            return answer.equals("3");
        if (number==3)
            return answer.equals("blame");
        if (number==4)
            return answer.contains("get") && answer.contains("post") &&
                    answer.contains("put") && answer.contains("patch")
                    && answer.contains("delete");
        return false;
    }

    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }
}
