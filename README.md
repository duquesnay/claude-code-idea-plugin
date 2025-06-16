# Claude Code IntelliJ IDEA Plugin

Integrate Claude directly into your IntelliJ IDEA workflow with this plugin. It adds a dedicated Claude tool window in the right sidebar that runs the `claude` command in an embedded terminal.

## Features

- Dedicated "Claude Code" tool window in the right sidebar
- Multiple Claude sessions in tabs
- Automatically launches `claude` when opened
- Configurable startup options:
  - Continue previous conversation (-c) - enabled by default
  - Custom model selection (sonnet, opus, etc.)
  - Additional command-line flags
- Quick access to settings from tool window gear menu
- Automatic restoration of tool window state on project restart
- Keyboard shortcut (Ctrl+Shift+N) for new sessions
- Seamless integration with the IDE's terminal
- Custom Claude Code icon in the tool window

## Prerequisites

- IntelliJ IDEA 2023.3 or later
- Claude CLI installed and available in your PATH
- Java 17 or later

## Installation

### From Release (Recommended)

1. Download the latest `claude-code-plugin-*.zip` from the releases
2. In IntelliJ IDEA, go to **Settings/Preferences** → **Plugins**
3. Click the gear icon and select **Install Plugin from Disk...**
4. Select the downloaded ZIP file
5. Restart IntelliJ IDEA

### From Source

1. Clone this repository
2. Build the plugin:
   ```bash
   ./gradlew buildPlugin
   ```
3. The plugin ZIP will be created in `build/distributions/`
4. Install using the steps above

## Usage

1. After installation, you'll see a "Claude Code" tab in the right sidebar of your IDE
2. Click the tab to open the Claude terminal
3. The `claude` command will start automatically with your configured options
4. Use Claude as you normally would in a terminal

### Multiple Sessions

- Click the gear icon in the tool window and select "New Claude Session"
- Or use the keyboard shortcut **Ctrl+Shift+N** when the tool window is focused
- Each session runs independently with its own conversation context

### Configuration

Access settings in two ways:
1. Click the gear icon in the Claude Code tool window → "Claude Settings"
2. Go to **Settings/Preferences** → **Tools** → **Claude Code**

Configure startup options:
- **Continue previous conversation**: Adds `-c` flag to continue your last conversation (enabled by default)
- **Custom model**: Select a specific model (sonnet, opus, etc.)
- **Additional flags**: Add any other command-line options like `--debug` or `--verbose`

## Development

### Building

```bash
# Build the plugin
./gradlew buildPlugin

# Run tests
./gradlew test

# Run IDE with the plugin for testing
./gradlew runIde
```

### Project Structure

- `src/main/kotlin/` - Plugin source code
- `src/main/resources/` - Plugin resources (XML configuration, icons)
- `src/test/kotlin/` - Unit tests

### Testing

The plugin includes unit tests for the main components. Run them with:

```bash
./gradlew test
```

## Troubleshooting

### Claude command not found

If you see an error about `claude` not being found:

1. Ensure Claude is installed: `which claude`
2. If installed via npm, ensure npm bin is in your PATH
3. Restart IntelliJ IDEA after installing Claude

### Tool window doesn't appear

1. Check **View** → **Tool Windows** → **Claude Code**
2. Ensure the plugin is enabled in **Settings** → **Plugins**
3. Try restarting IntelliJ IDEA

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For issues or feature requests, please create an issue on GitHub.