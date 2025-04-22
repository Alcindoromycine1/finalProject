Rudra Garg numero uno slacker

UPDATED ASCND CODE:

import os
import shutil
import git
import requests
import json
import traceback
import time
import sys  # For writing to stdout without a newline
from PIL import Image

CONFIG_FILE = "config.json"

# ANSI escape codes for colors
GREEN = '\033[92m'
YELLOW = '\033[93m'
RED = '\033[91m'
RESET = '\033[0m'

def colored(text, color):
    return f"{color}{text}{RESET}"

def save_config(eclipse_dir=None, repo_url=None, token=None, downloads_path=None):
    config_data = {}
    if os.path.exists(CONFIG_FILE):
        with open(CONFIG_FILE, "r") as f:
            config_data = json.load(f)

    if eclipse_dir:
        config_data["eclipse_dir"] = eclipse_dir
    if repo_url:
        config_data["repo_url"] = repo_url
    if token:
        config_data["github_token"] = token  # Use a more specific key
    if downloads_path:
        config_data["downloads_path"] = downloads_path

    with open(CONFIG_FILE, "w") as f:
        json.dump(config_data, f, indent=4) # Added indent for readability

def load_config():
    if os.path.exists(CONFIG_FILE):
        with open(CONFIG_FILE, "r") as f:
            return json.load(f)
    return None

def get_latest_commit_sha(repo_url, token):
    owner, repo = repo_url.split('/')[-2], repo_url.split('/')[-1].replace('.git', '')
    api_url = f"https://api.github.com/repos/{owner}/{repo}/branches/main"
    headers = {'Authorization': f'token {token}'}
    print("Fetching latest commit SHA...", end=" ")
    sys.stdout.flush()
    try:
        response = requests.get(api_url, headers=headers)
        response.raise_for_status()
        print(colored("Done.", GREEN))
        return response.json()['commit']['sha']
    except requests.exceptions.RequestException as e:
        print(colored("Error.", RED))
        print(f"Error fetching latest commit SHA: {e}")
        return None

def create_github_branch(repo_url, token, branch_name, latest_commit_sha):
    if latest_commit_sha is None:
        print("Cannot create branch without the latest commit SHA.")
        return False
    owner, repo = repo_url.split('/')[-2], repo_url.split('/')[-1].replace('.git', '')
    api_url = f"https://api.github.com/repos/{owner}/{repo}/git/refs"
    headers = {'Authorization': f'token {token}'}

    print(f"Creating new branch: {branch_name}...", end=" ")
    sys.stdout.flush()
    data = {"ref": f"refs/heads/{branch_name}", "sha": latest_commit_sha}
    try:
        response = requests.post(api_url, json=data, headers=headers)
        response.raise_for_status()
        print(colored("Done.", GREEN))
        print(f"Branch {branch_name} created successfully on GitHub.")
        return True
    except requests.exceptions.RequestException as e:
        if "Reference already exists" in response.text:
            print(colored("Exists.", YELLOW))
            print(f"Branch {branch_name} already exists. Proceeding...")
            return True # Consider it successful for the next steps
        else:
            print(colored("Error.", RED))
            print(f"Error creating branch: {response.text}")
            return False

def sanitize_branch_name(branch_name):
    return branch_name.strip().replace(" ", "-").replace(".", "-").lower()

def upload_to_eclipse(downloads_folder, workspace_dir):
    src_folder = os.path.join(downloads_folder, "src")
    if not os.path.exists(src_folder):
        print(colored(f"Error: The 'src' folder was not found at {src_folder}", RED))
        input("Press Enter to exit...")
        return False

    target_src_folder = os.path.join(workspace_dir, "src")
    print(f"Target src folder at: {target_src_folder}")

    if os.path.exists(target_src_folder):
        try:
            shutil.rmtree(target_src_folder)
            print(f"Removed existing {target_src_folder}")
        except OSError as e:
            print(colored(f"Error removing existing {target_src_folder}: {e}", RED))
            input("Press Enter to exit...")
            return False

    try:
        shutil.copytree(src_folder, target_src_folder)
        print(colored(f"Replaced {target_src_folder} with {src_folder}", GREEN))
        return True
    except OSError as e:
        print(colored(f"Error copying from {src_folder} to {target_src_folder}: {e}", RED))
        input("Press Enter to exit...")
        return False

def upload_to_github(eclipse_dir, repo_url, token, branch_name):
    repo_dir = os.path.join(os.path.expanduser("~"), 'temp_repo_github_upload')
    repo = None
    branch_created = False
    push_successful = False

    try:
        src_folder_local = os.path.join(eclipse_dir, "src")
        if not os.path.exists(src_folder_local):
            print(colored(f"Error: The 'src' folder does not exist in the project directory: {src_folder_local}", RED))
            return False

        if not os.path.exists(repo_dir):
            print(f"Cloning repository {repo_url}...", end=" ")
            sys.stdout.flush()
            repo = git.Repo.clone_from(repo_url, repo_dir)
            print(colored("Done.", GREEN))
            print(colored("Repository cloned successfully.", GREEN))
        else:
            print(f"Repository already cloned. Pulling latest changes...", end=" ")
            sys.stdout.flush()
            repo = git.Repo(repo_dir)
            repo.remotes.origin.pull()
            print(colored("Done.", GREEN))
            print(colored("Latest changes pulled.", GREEN))

        latest_commit_sha = get_latest_commit_sha(repo_url, token)
        if latest_commit_sha:
            branch_created = create_github_branch(repo_url, token, branch_name, latest_commit_sha)

            if branch_created:
                print(f"Fetching the latest updates from remote repository...", end=" ")
                sys.stdout.flush()
                repo.git.fetch()
                print(colored("Done.", GREEN))

                print(f"Checking out to branch: {branch_name}...", end=" ")
                sys.stdout.flush()
                try:
                    repo.git.checkout(branch_name)
                    print(colored("Done.", GREEN))

                    src_folder_repo = os.path.join(repo_dir, "src")
                    if os.path.exists(src_folder_repo):
                        shutil.rmtree(src_folder_repo)
                    print(f"Copying 'src' folder...", end=" ")
                    sys.stdout.flush()
                    shutil.copytree(src_folder_local, src_folder_repo)
                    print(colored("Done.", GREEN))
                    print(f"Replaced the 'src' folder in the repository with the one from {eclipse_dir}")

                    print("Adding changes...", end=" ")
                    sys.stdout.flush()
                    repo.git.add('src')
                    print(colored("Done.", GREEN))

                    print("Committing changes...", end=" ")
                    sys.stdout.flush()
                    repo.git.commit('-m', f"Replace src folder with the new version from {eclipse_dir}")
                    print(colored("Done.", GREEN))

                    print(f"Pushing changes to GitHub on branch {branch_name}...", end=" ")
                    sys.stdout.flush()
                    repo.git.push('origin', branch_name)
                    print(colored("Done.", GREEN))
                    push_successful = True
                    print(colored(f"\nSUCCESS: The updated 'src' folder has been successfully uploaded to GitHub on the branch: {branch_name}", GREEN))
                    print(colored(f"\n**IMPORTANT:** The branch '{branch_name}' has been created on GitHub.", YELLOW))
                    print(f"Please go to your GitHub repository ({repo_url}) to create a Pull Request to merge this branch with your 'main' branch.")
                    return True

                except git.exc.GitCommandError as e:
                    print(colored("Error.", RED))
                    print(f"Error during Git operations after branch creation: {e}")
                    print(f"Git command output (stderr):\n{e.stderr}")
                    return False
    except Exception as e:
        print(colored(f"An unexpected error occurred: {e}", RED))
        traceback.print_exc()
        return False
    finally:
        if os.path.exists(repo_dir):
            print(f"\nAttempting to remove temporary repository directory '{repo_dir}' in 10 seconds...", end=" ")
            sys.stdout.flush()
            time.sleep(10)  # Wait for 10 seconds
            print("Now attempting to remove...")
            try:
                shutil.rmtree(repo_dir)
                print(colored("Temporary repository directory removed.", GREEN))
            except OSError as e:
                print(colored(f"Error removing temporary repository directory '{repo_dir}': {e}", RED))
                print(f"The temporary repository is located at: {colored(repo_dir, YELLOW)}")
                print(f"Please ensure no other applications are using files within this directory and try deleting it manually.")
    return push_successful

def main():

    config = load_config()
    upload_type = ""
    eclipse_dir = config.get("eclipse_dir") if config else None
    repo_url = config.get("repo_url") if config else None
    token = config.get("github_token") if config else None # Use the new key
    downloads_path = config.get("downloads_path") if config else None

    while True:
        upload_choice = input("Do you want to upload to 'Eclipse' or 'Github'? ").strip().lower()
        if upload_choice == "eclipse" or upload_choice == "github":
            upload_type = upload_choice.capitalize()
            break
        else:
            print(colored("Invalid choice. Please enter 'Eclipse' or 'Github'.", RED))

    if config:
        use_previous = input(f"Use previous settings? (Y/N): ").strip().lower()
        if use_previous == 'y':
            pass # Use the loaded values
        else:
            if upload_type == "Eclipse":
                downloads_path = input("Enter the full path to the 'Downloads' folder: ").strip()
                eclipse_dir = input("Enter the Eclipse workspace directory: ").strip()
            elif upload_type == "Github":
                eclipse_dir = input("Enter the path to your Eclipse project: ").strip()
                repo_url = input("Enter the GitHub repository URL: ").strip()
                token = input("Enter your GitHub personal access token: ").strip()
            save_config(eclipse_dir, repo_url, token, downloads_path)
    else:
        if upload_type == "Eclipse":
            downloads_path = input("Enter the full path to the 'Downloads' folder: ").strip()
            eclipse_dir = input("Enter the Eclipse workspace directory: ").strip()
        elif upload_type == "Github":
            eclipse_dir = input("Enter the path to your Eclipse project: ").strip()
            repo_url = input("Enter the GitHub repository URL: ").strip()
            token = input("Enter your GitHub personal access token: ").strip()
        save_config(eclipse_dir, repo_url, token, downloads_path)

    print(f"Selected upload type: {colored(upload_type, YELLOW)}")
    print(f"Eclipse project directory:", eclipse_dir)

    if upload_type == "Eclipse":
        print("Downloads folder:", downloads_path)
        if downloads_path and eclipse_dir:
            upload_to_eclipse(downloads_path, eclipse_dir)
    elif upload_type == "Github":
        print("GitHub repository URL:", repo_url)
        print("Personal access token provided.")
        branch_name_input = input("Enter the name for your new branch (for merge request): ").strip()
        branch_name = sanitize_branch_name(branch_name_input)
        print(f"Sanitized branch name: {branch_name}")
        if eclipse_dir and repo_url and token and branch_name:
            upload_to_github(eclipse_dir, repo_url, token, branch_name)

    input("Press Enter to exit...")

if __name__ == "__main__":
    main()


