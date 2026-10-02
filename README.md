# OpenCity

<table>
  <tr>

## !! ACTIVE DEVELOPMENT !!

**This project is changing rapidly.**  
The README is currently out of date and does **not** necessarily reflect the current status of the project.

  </tr>
</table>


## Report and solve urban problems
OpenCity is a free and open-source platform that allows users to report, track, and discuss problems in their cities.

Users can report issues such as:
- Potholes
- Broken streetlights
- Damaged traffic signs
- Graffiti
- Broken public equipment
- Accessibility problems
- Other urban infrastructure issues

Reports are displayed on an interactive map, allowing citizens and administrators to follow their progress from the initial report to its resolution.

## Features
- Create and manage user accounts
- Report urban problems with:
  - GPS-based location / Manually selected map location
  - Photo upload (Automatic metadata stripping from uploaded images)
  - Description/caption
  - Select category and severity
- View nearby reports on a top-down map
- Filter reports by:
  - Category
  - Severity or urgency
  - Status
  - Distance from a marker
- Confirm or dispute the validity of reports
- Comment on reports
- Track report history and status changes
- Receive notifications about relevant reports
- Earn points for useful reports and confirmations
- Export contributed reports as CSV or JSON
- Define custom map markers
- Download map data for offline use
- Support multiple map orientation modes
- Provide an administration dashboard

## Report Statuses

Administrators can update the status of a report. The initial status set may include:
- `Reported`
- `Under Review`
- `Confirmed`
- `Being Repaired`
- `Resolved`
- `Rejected`
- `Duplicate`

The status system can be extended or customized by the server administrator.

## User Roles
### Anonymous Users
Anonymous users can:
- View the map
- View public reports
- Filter reports
- View report details

### Registered Users
Registered users can:
- Create reports
- Comment on reports
- Confirm or dispute reports
- Follow reports
- Receive notifications
- Earn points
- View their contribution history
- Export their reports and contributions
- Manage their account settings

### Administrators
Administrators can:
- Review reports
- Update report statuses
- Manage categories and severity levels
- Moderate comments
- Manage users
- Manage custom markers
- Access the administration dashboard
- View statistics
- Configure server settings

## Application Structure
### Map
The map is the default application screen and provides:
- A top-down map view
- Report markers
- Custom user markers
- Report filtering
- Marker management access
- Optional heatmap statistics
- Optional path tracing
- Optional offline map support

Users can choose between the following orientation modes:
- North-up
- Manual rotation
- Compass sensor

### Reports
A report contains:

- Title or description
- Category
- Severity
- Location
- Photo
- Creation date
- Author
- Current status
- Confirmation and dispute counts
- Comments
- Status history

When a photo is uploaded, metadata such as GPS coordinates, device information, and timestamps are removed before the file is stored or shared.

### Notifications
Users may receive notifications when:
- They are within a configured distance of a marker
- A nearby report exceeds a configured severity level
- A report they created changes status
- A report they contributed to receives an update
- A report they participated in is resolved or closed
- Someone comments on one of their reports

Notifications can configured in the application settings.

### Marker Management
Users can access a marker management screen for custom markers.
On the server, a maximum of syncronised markers is definable.
Markers may be:
- Created
- Edited
- Removed
- Stored locally
- Synchronized with the server
Markers can be used as the user sees fit.

### Account Management
Account settings include:

- Profile picture
- Username
- Optional display name
- Password management / Google login
- Points and contribution history
- Data export
- Account deletion
- Terms of use
- Privacy policy
- Logout

At least three access modes are supported:
- Anonymous
- User
- Administrator

The server may require administrator approval before an account can access certain features.

## Points System
OpenCity includes an optional points system associated with registered accounts.

Users may earn points for:
- Submitting useful reports
- Confirming valid reports
- Providing helpful information
- Contributing comments
- Helping verify or update reports

The exact scoring rules are configurable by the server administrator.

## Administration Dashboard

The administration dashboard allows authorized users to:

- Review new reports
- Change report statuses
- Manage categories
- Adjust severity levels
- Moderate comments
- Manage user accounts
- Manage markers
- View report statistics
- Inspect report history
- Export data
- Configure notification rules

## Server
OpenCity is designed to work with a user-selected server, similar to the Moodle platform.

Users can configure the server connection using:
- Server address
- IP address or hostname
- Port
- Optional server password

Changing the configured server requires the user to log out first, as accounts are per server.

### Server Responsibilities

The server provides:

- REST API
- User authentication
- Database storage
- Report synchronization
- Photo and file uploads
- Comment storage
- Notification delivery
- Points management
- Administration functionality
- Data export
- Optional synchronization with other OpenCity servers

### Server Synchronization
The system may support synchronization between multiple OpenCity servers.

This can allow:
- Sharing reports between servers
- Synchronizing public markers
- Replicating selected map data
- Connecting different cities or communities
- Operating separate instances with shared information

Synchronization rules and permissions should be configurable by server administrators.

## API

The server exposes a REST API for communication with the mobile application and other compatible clients.
The API supports:

- Authentication and authorization
- Report creation and editing
- Photo uploads
- Comments
- Status updates
- Map and marker queries
- Filtering
- Data export
- Server synchronization

## Map Data
OpenCity can use open geographic data from OpenStreetMap.

## Sustainable Development Goals
OpenCity was designed with the United Nations Sustainable Development Goal 11 in mind:
> Make cities and human settlements inclusive, safe, resilient, and sustainable.

The project contributes to this goal by supporting:
- Better urban management
- Citizen participation in city planning
- Improved public infrastructure
- Faster identification of local problems
- Transparent communication between citizens and administrators

## Technology
### Application
- Kotlin
- Android application
- Map-based user interface
- REST API client
- Local data storage
- Optional offline maps

### Server
The server implementation may use:
- REST API
- Relational or document database
- File storage for uploaded images
- Authentication and authorization
- Notification services
- Synchronization services

The exact server-side technology can be selected independently, provided it remains compatible with the OpenCity API.

## Functional Requirements
### Report Creation
The application must allow users to:
- Detect their location using GPS **OR** Select a location manually on the map
- Upload a photo
- Strip metadata from uploaded photos
- Add a description
- Select a category
- Select or assign a severity level
- Submit the report to a server

### Report Interaction
Users must be able to:
- View report details
- Confirm that a report is valid
- Dispute or reject a report
- Add comments
- Follow report updates
- View the report status history

Administrators must be able to:
- Review reports
- Update report statuses
- Edit report categories
- Moderate content
- Resolve or reject reports

### Map
The default activity must provide:
- A top-down map
- Report markers
- Marker filtering
- Access to custom marker management
- Optional path tracing
- Optional heatmap statistics
- Optional offline map support

### Settings
The application settings must include:
- Dark mode and light mode
- Animations on/off
- Notification preferences
- Map settings
- Orientation mode
- Offline map downloads
- Marker management
- Account settings
- Server configuration
- Privacy and data management

### Contribution History
Users must be able to:
- View reports they created
- View reports they commented on
- View reports they confirmed or disputed
- Check their points
- Export their contributions as CSV or JSON

## Non-Functional Requirements
- The application must be developed using Kotlin.
- The project must be free and open-source software.
- The application should support multiple server instances.
- The application should work with open geographic data.
- Uploaded image metadata should be removed before storage.
- The system should support anonymous read-only access.
- The system should use role-based permissions.
- The application should support configurable server connections.
- The application should be designed for accessibility and usability.
- The system should protect user accounts and uploaded content.

## Optional Features
The following features may be implemented in future versions:
- Offline maps
- Heatmap statistics
- Custom marker icons
- Custom marker colors
- Google login
- Automatic duplicate report detection
- Automatic report categorization

## Project Status
OpenCity is currently under development.
The project is being designed as a modular, extensible, and community-oriented platform for reporting and managing urban problems.

## Contributing
As of now, we are not allowing contributions, as we are being graded on the development of the project.
Once the grading is finished, we will be open to contribuitors.

## License
OpenCity is intended to be released as free and open-source software.
The final license is yet to be defined.
