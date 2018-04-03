import {NativeModules, DeviceEventEmitter} from "react-native";
import NotificationAndroid from "./notification";

const RNNotifications = NativeModules.WixRNNotifications;

let notificationReceivedListener;
let notificationOpenedListener;
let registrationTokenUpdateListener;
let actionListenerMap = new Map();
let _actionListenerMap = new Map();


export class NotificationAction {
  options: Object;
  handler: Function;

  constructor(options: Object, handler: Function) {
    this.options = options;
    this.handler = handler;
  }
}

export class NotificationCategory {
  options: Object;

  constructor(options: Object) {
    this.options = options;
  }
}

export class NotificationsAndroid {


  static setNotificationActionListener(actionId,listener) {
    actionListenerMap.set(actionId,  DeviceEventEmitter.addListener(actionId, (notification) => listener(new NotificationAndroid(notification))));
  } 

  static clearNotificationActionListener(actionId) {
    actionListenerMap.get(actionId).remove();
    actionListenerMap.delete(actionId);
  }

  static setNotificationCategory(categories: Array<NotificationCategory>){
    let notificationCategories = [];

    if (categories) {
      notificationCategories = categories.map(category => {
              return Object.assign({}, category.options, {
                actions: category.options.actions.map(action => {
                  // subscribe to action event
                  actionListenerMap.set(
                    category.options.identifier+ "-" + action.options.identifier, 
                    DeviceEventEmitter.addListener(category.options.identifier+ "-" + action.options.identifier , action.handler));

                  return action.options;
                })
              });
            });
       
    }
    RNNotifications.setNotificationCategory(notificationCategories);
  }



  static setNotificationOpenedListener(listener) {
    notificationOpenedListener = DeviceEventEmitter.addListener("notificationOpened", (notification) => listener(new NotificationAndroid(notification)));
  }

  static clearNotificationOpenedListener() {
    if (notificationOpenedListener) {
      notificationOpenedListener.remove();
      notificationOpenedListener = null;
    }
  }

  static setNotificationReceivedListener(listener) {
    notificationReceivedListener = DeviceEventEmitter.addListener("notificationReceived", (notification) => listener(new NotificationAndroid(notification)));
  }

  static clearNotificationReceivedListener() {
    if (notificationReceivedListener) {
      notificationReceivedListener.remove();
      notificationReceivedListener = null;
    }
  }

  static setRegistrationTokenUpdateListener(listener) {
    registrationTokenUpdateListener = DeviceEventEmitter.addListener("remoteNotificationsRegistered", listener);
  }

  static clearRegistrationTokenUpdateListener() {
    if (registrationTokenUpdateListener) {
      registrationTokenUpdateListener.remove();
      registrationTokenUpdateListener = null;
    }
  }

  static async isRegisteredForRemoteNotifications() {
    return await RNNotifications.isRegisteredForRemoteNotifications();
  }

  static refreshToken() {
    RNNotifications.refreshToken();
  }

  static localNotification(notification: Object) {
    const id = Math.random() * 100000000 | 0; // Bitwise-OR forces value onto a 32bit limit
    RNNotifications.postLocalNotification(notification, id);
    return id;
  }

  static getPushToken(){
    RNNotifications.getPushToken()
  }

  static cancelLocalNotification(id) {
    RNNotifications.cancelLocalNotification(id);
  }
}

export class PendingNotifications {
  static getInitialNotification() {
    return RNNotifications.getInitialNotification()
      .then((rawNotification) => {
        return rawNotification ? new NotificationAndroid(rawNotification) : undefined;
      });
  }
}
