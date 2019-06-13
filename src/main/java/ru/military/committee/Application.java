package ru.military.committee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Тема ВКР: "Разработка информационной системы для организации работы приеной комиссии военного училища".
 * Выполнил: Ивченко А.Д., специальность - 09.05.01, группа - 4414.
 * Руководитель ВКР: Пруцков А.В., д-р техн. наук, проф. каф. ВПМ.
 * Средства разработки: Java 8, Spring Boot, СУБД MySQL, Bootstrap 3.
 * Класс включается в себя метод main, являющийся точкой входа в приложение.
 * Дата разработки: 17.03.19.
 */

@SpringBootApplication
public class Application {
    /**
     * Точка входа в приложение.
     * @param args - аргументы, с которыми запускается приложение.
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
