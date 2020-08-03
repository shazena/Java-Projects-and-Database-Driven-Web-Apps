package com.skshazena.flooringmastery.controller;

import com.skshazena.flooringmastery.dao.FlooringPersistenceException;
import com.skshazena.flooringmastery.dto.Order;
import com.skshazena.flooringmastery.dto.ProductType;
import com.skshazena.flooringmastery.service.FlooringNoMatchingOrdersException;
import com.skshazena.flooringmastery.service.FlooringNoSuchOrderException;
import com.skshazena.flooringmastery.service.FlooringOrderValidationException;
import com.skshazena.flooringmastery.service.FlooringService;
import com.skshazena.flooringmastery.ui.FlooringView;
import java.time.LocalDate;
import java.util.List;

/**
 * Flooring Mastery Project Controller
 *
 * @author Shazena Khan
 *
 * Date Created: Jun 28, 2020
 */
public class FlooringController {

    private FlooringView view;
    private FlooringService service;

    public FlooringController(FlooringView view, FlooringService service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepRunning = true;

        view.welcomeMessage();
        int menuSelection = 0;
        while (keepRunning) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    displayOrders();
                    break;
                case 2:
                    addAnOrder();
                    break;
                case 3:
                    editAnOrder();
                    break;
                case 4:
                    removeAnOrder();
                    break;
                case 5:
                    exportAllData();
                    break;
                default:
                    keepRunning = false;
                    break;
            }
        }
        exitMessage();

    }

    private int getMenuSelection() {
        return view.displayMainMenuAndGetSelection();
    }

    private void displayOrders() {
        try {
            view.displayDisplayOrdersBanner();
            LocalDate dateOfOrder = view.getDateOfOrderFromUser();
            List<Order> listOfOrders = service.getAllOrdersForSpecifiedDate(dateOfOrder);
            if (listOfOrders != null) {
                view.displayOrders(listOfOrders, dateOfOrder);
            }
        } catch (FlooringPersistenceException | FlooringNoMatchingOrdersException ex) {
            view.displayErrorMessage(ex.getMessage());
        }
    }

    private void addAnOrder() {
        boolean orderIsInvalid = true;
        while (orderIsInvalid) {
            try {
                view.displayAddBanner();
                List<ProductType> listOfProducts = service.getAllProducts();
                Order newOrder = view.getNewOrderInfo(listOfProducts);
                newOrder = service.validateAndCalculateOrder(newOrder, newOrder.getOrderNumber());
                view.displayDetailsOfOneOrder(newOrder);
                String userApprovalOfOrder = view.getUserApprovalOfOrder();
                boolean wasOrderAdded = service.addAnOrder(newOrder, userApprovalOfOrder);
                view.displaySuccessOrDiscardBanner(userApprovalOfOrder, wasOrderAdded);
                orderIsInvalid = false;
            } catch (FlooringOrderValidationException e) {
                Throwable[] allErrors = e.getSuppressed();
                for (Throwable error : allErrors) {
                    view.displayErrorMessage(error.getMessage());
                }
            } catch (FlooringPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }
    }

    private void editAnOrder() {
        boolean orderIsInvalid = true;
        LocalDate dateOfOrderFromUser;
        int orderNumberFromUser;
        Order anOrder = null;
        try {
            view.displayEditBanner();
            dateOfOrderFromUser = view.getDateOfOrderFromUser();
            orderNumberFromUser = view.getOrderNumberFromUser();
            anOrder = service.getAnOrder(dateOfOrderFromUser, orderNumberFromUser);
        } catch (FlooringPersistenceException | FlooringNoSuchOrderException e) {
            orderIsInvalid = false;
            view.displayErrorMessage(e.getMessage());
        }

        while (orderIsInvalid) {
            try {
                List<ProductType> listOfProducts = service.getAllProducts();
                Order editedOrder = view.getEditedOrderInfo(anOrder, listOfProducts);
                editedOrder = service.editValidateAndCalculateOrder(anOrder, editedOrder);
                view.displayDetailsOfOneOrder(editedOrder);
                String userApprovalOfOrder = view.getUserApprovalOfOrder();
                boolean wasOrderEdited = service.editAnOrder(editedOrder, userApprovalOfOrder);
                view.displaySuccessOrDiscardBanner("edited", wasOrderEdited);
                orderIsInvalid = false;
            } catch (FlooringOrderValidationException e) {
                Throwable[] allErrors = e.getSuppressed();
                for (Throwable error : allErrors) {
                    view.displayErrorMessage(error.getMessage());
                }
            } catch (FlooringPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }

    }

    private void removeAnOrder() {
        try {
            view.displayRemoveBanner();
            LocalDate dateOfOrderFromUser = view.getDateOfOrderFromUser();
            int orderNumberFromUser = view.getOrderNumberFromUser();
            Order anOrder = service.getAnOrder(dateOfOrderFromUser, orderNumberFromUser);
            view.displayDetailsOfOneOrder(anOrder);
            String userApprovalOfOrder = view.getUserApprovalOfOrder();
            boolean wasOrderRemoved = service.removeAnOrder(dateOfOrderFromUser, orderNumberFromUser, userApprovalOfOrder);
            view.displaySuccessOrDiscardBanner("removed", wasOrderRemoved);
        } catch (FlooringPersistenceException | FlooringNoSuchOrderException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void exportAllData() {
        try {
            view.displayExportBanner();
            service.exportAllData();
            view.displaySuccessfulExport();
        } catch (FlooringPersistenceException ex) {
            view.displayErrorMessage(ex.getMessage());
        }
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

}
