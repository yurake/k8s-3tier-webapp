var users = [
  {
    user: 'webapp',
    pwd: 'webapp',
    roles: [
      {
        role: 'readWrite',
        db: 'webapp'
      }
    ]
  }
];

for (var i = 0, length = users.length; i < length; ++i) {
  db.createUser(users[i]);
}
