# Claude Code IntelliJ IDEA Plugin

Integrate Claude Code directly into your IntelliJ IDEA workflow with this plugin. It adds a dedicated Claude Code tool window that runs the `claude-code` command in an embedded terminal.

## Features

- Dedicated "Claude Code" tool window in IntelliJ IDEA
- Automatically launches `claude-code` when opened
- Seamless integration with the IDE's terminal
- Custom Claude Code icon in the tool window

## Prerequisites

- IntelliJ IDEA 2023.3 or later
- Claude Code CLI installed and available in your PATH
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

1. After installation, you'll see a "Claude Code" tab at the bottom of your IDE
2. Click the tab to open the Claude Code terminal
3. The `claude-code` command will start automatically
4. Use Claude Code as you normally would in a terminal

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

### Claude Code command not found

If you see an error about `claude-code` not being found:

1. Ensure Claude Code is installed: `which claude-code`
2. If installed via npm, ensure npm bin is in your PATH
3. Restart IntelliJ IDEA after installing Claude Code

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