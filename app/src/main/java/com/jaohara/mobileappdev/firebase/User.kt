package com.jaohara.mobileappdev.firebase

class User (username: String?, email: String?, updated: String?) {
  var username: String?;
  var email: String?;
  var updated: String?;

  constructor() : this(null, null, null);

  init {
    this.username = username;
    this.email = email;
    this.updated = updated;
  }
}