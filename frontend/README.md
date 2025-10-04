# React Frontend Setup

This is the starter guide for setting up the React SPA for ProfilePortfolio.

## 1. Scaffold the app

From the root of the repository, run:
```bash
npx create-react-app frontend
```

## 2. Key files to add/change

- `src/App.js`: Handles routing and authentication context.
- `src/auth.js`: Manages authentication state.
- `src/LoginPage.js`: Login form.
- `src/ProfilePage.js`: Profile display.

Sample contents:

### src/App.js
```javascript
import React from "react";
import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom";
import { AuthProvider, useAuth } from "./auth";
import LoginPage from "./LoginPage";
import ProfilePage from "./ProfilePage";

function PrivateRoute({ children, ...rest }) {
  const { user } = useAuth();
  return (
    <Route
      {...rest}
      render={({ location }) =>
        user ? (children) : (<Redirect to={{ pathname: "/login", state: { from: location } }} />)
      }
    />
  );
}

function App() {
  return (
    <AuthProvider>
      <Router>
        <Switch>
          <Route path="/login"><LoginPage /></Route>
          <PrivateRoute path="/profile"><ProfilePage /></PrivateRoute>
          <Redirect to="/profile" />
        </Switch>
      </Router>
    </AuthProvider>
  );
}

export default App;
```

### src/auth.js
```javascript
import React, { createContext, useContext, useState } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  const login = (username, token) => setUser({ username, token });
  const logout = () => setUser(null);

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
```

### src/LoginPage.js
```javascript
import React, { useState } from "react";
import { useHistory } from "react-router-dom";
import { useAuth } from "./auth";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { login } = useAuth();
  const history = useHistory();

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Call backend
    const res = await fetch("/api/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password }),
    });
    const data = await res.json();
    if (data.token) {
      login(username, data.token);
      history.push("/profile");
    } else {
      alert("Login failed");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input value={username} onChange={e => setUsername(e.target.value)} placeholder="Username" />
      <input value={password} onChange={e => setPassword(e.target.value)} placeholder="Password" type="password" />
      <button type="submit">Login</button>
    </form>
  );
}
```

### src/ProfilePage.js
```javascript
import React, { useEffect, useState } from "react";
import { useAuth } from "./auth";

export default function ProfilePage() {
  const { user, logout } = useAuth();
  const [profile, setProfile] = useState(null);

  useEffect(() => {
    fetch("/api/profile", {
      headers: { Authorization: `Bearer ${user.token}` }
    })
      .then(res => res.text())
      .then(setProfile);
  }, [user]);

  return (
    <div>
      <h1>Profile</h1>
      <div>{profile}</div>
      <button onClick={logout}>Logout</button>
    </div>
  );
}
```

---
## 3. Start the app

From `/frontend` folder:
```bash
npm start
```