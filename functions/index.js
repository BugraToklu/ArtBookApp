const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.sendWelcomeNotification = functions.auth.user().onCreate((user) => {
  const payload = {
    notification: {
      title: 'Welcome!',
      body: 'Thanks for signing up!'
    }
  };

  return admin.messaging().sendToTopic('allUsers', payload)
    .then(response => {
      console.log('Successfully sent message:', response);
    })
    .catch(error => {
      console.log('Error sending message:', error);
    });
});
